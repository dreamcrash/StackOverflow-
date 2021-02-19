/* 
 * File:   main.h
 * Author: Dreamcrash
 *
 */

#ifndef MAIN_H
#define MAIN_H

#ifdef __cplusplus
extern "C" {
#endif

unsigned int rand_interval	(unsigned int min, unsigned int max);
void fillupRandomly		(int *m, int n, unsigned int min, unsigned int max);
void mergeSort			(int *X, int n, int *tmp);
void mergeSortAux		(int *X, int n, int *tmp);
void init			(int *a, int size);
void printArray			(int *a, int size);		
int isSorted			(int *a, int size);
#ifdef __cplusplus
}
#endif

#endif /* MAIN_H */
