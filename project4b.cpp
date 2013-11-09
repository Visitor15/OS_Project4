/*
 * 	CS3442			Operating Systems
 * 	Semester:    Fall 2013
 * 	Project 4:   	Process Synchronization, Part 2
 * 	Name:       		 Nick Champagne and John Mutabazi
 * 	Date:        		10/23/2013
 * 	File:        			project4b.cpp
 */

#include "project4b.h"

/*=============================================
 BEGIN PROGRAM
 ==============================================*/
int main() {
	readInData(unsorted_list);
	ARRAY_SIZE = unsorted_list.size();

	/*
	 * Uncomment this and you can make it dynamic! You just have to be able to evenly divide
	 * the number of elements in the list read in from the file.
	 */
//	std::cout << "How many processes would you like to spawn?" << std::endl;
//	std:: cout << "-> ";
//	std::cin >> CHILD_PROCESS_COUNT;

	WORKING_LIST_SIZE = ARRAY_SIZE / CHILD_PROCESS_COUNT;

	//	 Initializing shared memory and semaphore
	initSharedMemoryObjs();

	// 	Since we read data into a vector<long> we have to copy it over to a shared array
	for (int i = 0; i < unsorted_list.size(); i++) {
		p_master_unsorted_list[i] = unsorted_list.at(i);
	}
	//	Clearing the vector<long>, we don't need it anymore.
	unsorted_list.clear();

	spawnChildrenProcesses(CHILD_PROCESS_COUNT);

	int tmp_index = 0;
	while (*num_of_running_processes > 0 || tmp_index < ARRAY_SIZE) {

		// 	If a child process has appended 10 elements, the parent process will
		//	sort starting from 'tmp_index' to (tmp_index + 10)
		if (*should_sort && (*curr_index > (tmp_index + (INCREMENT - 1)))) {

			std::cout << "Parent process is sorting indexes: " << tmp_index
					<< " - " << (tmp_index + (INCREMENT - 1)) << std::endl;
			doQuickSort(p_master_list, tmp_index, tmp_index + (INCREMENT - 1));

			tmp_index = tmp_index + INCREMENT;
		}
	}

	std::cout << "\n=============================================" << std::endl;
	std::cout << "\tALL PROCESSES FINISHED!!" << std::endl;
	std::cout << "=============================================" << std::endl;

	std::cout << "\nPerforming final sort...\n\n";
	doQuickSort(p_master_list, 0, (ARRAY_SIZE - 1));
	writeContentsToFile(p_master_list, "project4b_SORTED.txt");
	exitAndCleanParentProcess();

	return EXIT_SUCCESS;
}

void initSharedMemoryObjs() {

	//	Global semaphore
	m_semaphore = (sem_t*) mmap(NULL, sizeof(sem_t), PROT_READ | PROT_WRITE,
	MAP_SHARED | MAP_ANONYMOUS, -1, 0);
	sem_init(m_semaphore, 1, 1);

	//	Set to true by a child process after it has appended 10 elements
	should_sort = (bool*) mmap(NULL, sizeof(bool), PROT_READ | PROT_WRITE,
	MAP_SHARED | MAP_ANONYMOUS, -1, 0);

	//	Used by the parent process to wait for child processes to finished
	//	before doing a final sort of the 'p_master_list' and cleaning up / exiting
	num_of_running_processes = (unsigned int*) mmap(NULL,
			sizeof(*num_of_running_processes),
			PROT_READ | PROT_WRITE,
			MAP_SHARED | MAP_ANONYMOUS, -1, 0);

	//	Global index used by child processes when appending their data to 'p_master_list'
	curr_index = (unsigned int*) mmap(NULL, sizeof(*curr_index),
	PROT_READ | PROT_WRITE,
	MAP_SHARED | MAP_ANONYMOUS, -1, 0);

	//	Initial work list shared child processes that use it to partition data after they are spawned
	p_master_unsorted_list = (long*) mmap(NULL, sizeof(long) * ARRAY_SIZE,
	PROT_READ | PROT_WRITE,
	MAP_SHARED | MAP_ANONYMOUS, -1, 0);

	//	Master list where child append their data after processing
	p_master_list = (long*) mmap(NULL, sizeof(long) * ARRAY_SIZE,
	PROT_READ | PROT_WRITE,
	MAP_SHARED | MAP_ANONYMOUS, -1, 0);
}

void spawnChildrenProcesses(unsigned int num_of_children) {

	std::cout << "Spawning " << num_of_children << " processe(s)" << std::endl;

	for (int i = 1; i <= num_of_children; i++) {
		pid = fork();

		switch (pid) {
		case -1: {
			//	Error has occurred
			std::cout << "An ERROR has occurred! Exiting";
			exit(EXIT_FAILURE);
			break;
		}
		case 0: {
			// 	Child process
			process_index = i;
			logSpawnedChild();
			doChildProcessWork();
			break;
		}
		default: {
			// 	Parent process
			break;
		}
		}
	}
}

void logSpawnedChild() {
	sem_wait(m_semaphore);
	*num_of_running_processes = *num_of_running_processes + 1;
	sem_post(m_semaphore);
}

void doChildProcessWork() {
	int offset = ((process_index - 1) * WORKING_LIST_SIZE);

	//	Assigning work to child process
	working_list = getWorkingList(p_master_unsorted_list, offset,
			WORKING_LIST_SIZE);

	std::cout << "CHILD " << process_index << " IS RUNNING" << std::endl;
	std::cout << "Working with " << working_list.size() << " elements"
			<< std::endl;

	doSelectionSort(working_list);
}

std::vector<long> getWorkingList(long* list, unsigned int offset,
		unsigned int length) {
	std::vector<long> tmp_list;

	for (int i = offset; i < (length + offset); i++) {
		tmp_list.push_back(list[i]);
	}

	return tmp_list;
}

void onChildProcessFinished() {
	exitAndCleanChildProcess();
}

void appendElementsToMasterList(long list[], unsigned int length) {
	sem_wait(m_semaphore);
	for (int i = 0; i < length; i++) {

		//	Appending elements from the buffer and incrementing
		//	the global index as we go.
		p_master_list[*curr_index] = list[i];

		*curr_index = *curr_index + 1;

		//	Hint to the parent process to sort these 10 elements
		*should_sort = true;
	}
	sem_post(m_semaphore);
}

void doSelectionSort(std::vector<long> &list) {
	long minIndex;
	long tempVal;
	long i;
	long j;

	const int BUFFER_SIZE = 10;
	long buffer[BUFFER_SIZE];

	for (i = 0; i < list.size(); i++) {
		minIndex = i;

		for (j = i + 1; j < list.size(); j++) {

			if (list.at(j) < list.at(minIndex)) {
				minIndex = j;
			}
		}

		if (minIndex != i) {
			tempVal = list.at(i);
			list.at(i) = list.at(minIndex);
			list.at(minIndex) = tempVal;
		}

		//	Adding elements to buffer
		buffer[i % BUFFER_SIZE] = list.at(i);
		if ((i + 1) % BUFFER_SIZE == 0) {
			appendElementsToMasterList(buffer, BUFFER_SIZE);
		}
	}

	onChildProcessFinished();
}

void doQuickSort(long* unsortedList, long leftMost, long rightMost) {
	long tempVal;
	long i = leftMost;
	long j = rightMost;
	long pivotVal = unsortedList[(leftMost + rightMost) / 2];

	while (i <= j) {
		while (unsortedList[i] < pivotVal)
			i++;
		while (unsortedList[j] > pivotVal)
			j--;
		if (i <= j) {
			tempVal = unsortedList[i];
			unsortedList[i] = unsortedList[j];
			unsortedList[j] = tempVal;
			i++;
			j--;
		}
	}

	if (leftMost < j) {
		doQuickSort(unsortedList, leftMost, j);
	}
	if (i < rightMost) {
		doQuickSort(unsortedList, i, rightMost);
	}
}

void readInData(std::vector<long> &list) {
	std::cout << "Reading data in..." << std::endl;
	std::ifstream inStream("numbers.txt");
	std::string m_line;
	while (std::getline(inStream, m_line)) {

		//	Converting from a string to an long.
		long number = strtol((char*) m_line.c_str(), NULL, 0);
		list.push_back(number);
	}
	inStream.close();

	std::cout << list.size() << " elements read" << std::endl;
}

void writeContentsToFile(long list[], std::string fileName) {
	std::cout << "Writing contents to file..." << std::endl;
	std::ofstream outFile((char*) fileName.c_str(), std::ios::app);

	for (int i = 0; i < ARRAY_SIZE; i++) {
		outFile << list[i] << std::endl;
	}
	outFile.close();

	std::cout << "Data written to: " << fileName << "\n\n";
}

void exitAndCleanChildProcess() {
	sem_wait(m_semaphore);

	std::cout << "CHILD " << process_index << " HAS FINISHED" << std::endl;

	*num_of_running_processes = *num_of_running_processes - 1;
	sem_post(m_semaphore);

	exit(EXIT_SUCCESS);
}

void exitAndCleanParentProcess() {
	sem_destroy(m_semaphore);
	munmap(m_semaphore, sizeof(sem_t));
	munmap(p_master_unsorted_list,
			sizeof(p_master_unsorted_list[0]) * ARRAY_SIZE);
	munmap(p_master_unsorted_list, sizeof(p_master_list[0]) * ARRAY_SIZE);
	munmap(num_of_running_processes, sizeof(*num_of_running_processes));

	exit(EXIT_SUCCESS);
}

