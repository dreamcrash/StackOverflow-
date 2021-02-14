#include <stdio.h>
#include <stdlib.h>
#include "cuda.h"
#include <algorithm>
#include <string>
#include <iostream>
#include <tr1/unordered_map>


typedef std::tr1::unordered_map<float, int> Mymap;


void generator(float *data, long int size)
{
    float LO = 0.0;
    float HI = 100.0;
    
    for(long int i = 0; i < size; i++)
        data[i] = LO + (float)rand()/((float)RAND_MAX/(HI-LO));
}

void print_array(float *data, long int size)
{

    for(long int i = 2; i < size; i++)
        printf("%f\n",data[i]);
    
}

std::tr1::unordered_map<float, int> fill_dict(float *data, int size)
{
    float previous = data[0];
    int count = 1;
    std::tr1::unordered_map<float, int> dict;
    
    for(long int i = 1; i < size; i++)
    {
        if(previous == data[i])
            count++;
        else
        {
          dict.insert(Mymap::value_type(previous,count));
          previous = data[i];
          count = 1;         
        }
        
    }
    dict.insert(Mymap::value_type(previous,count)); // add the last member
    return dict;
    
}

void printMAP(std::tr1::unordered_map<float, int> dict)
{
   for(std::tr1::unordered_map<float, int>::iterator i = dict.begin(); i != dict.end(); i++)
  {
     std::cout << "key(string): " << i->first << ", value(int): " << i->second << std::endl;
   }
}


int main(int argc, char** argv)
{
  int size = 1000000; 
  if(argc > 1) size = atoi(argv[1]);
  printf("Size = %d",size);
  
  float data[size];
  using namespace __gnu_cxx;
  
  std::tr1::unordered_map<float, int> dict;
  
  generator(data,size);
  
  sort(data, data + size);
  dict = fill_dict(data,size);
  
  return 0;
}
