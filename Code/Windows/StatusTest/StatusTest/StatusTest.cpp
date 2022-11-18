#include <stdio.h>
#include "GlobalData.h"

void receiveEvent(int data[],int len);


int main(void)
{
    int data[100];
    printf("Test\n");
    data[0] = 129;
    data[1] = 1;
    receiveEvent(data,2);
    checkStatus();
    data[0] = 129;
    data[1] = 2;
    receiveEvent(data, 2);
    checkStatus();
}

void receiveEvent(int data[],int len)
{
    int loc = 0;
    int command = (int)data[loc]; // receive byte as a character
    loc++;
    printf("Command %d:",command);         // print the character
    int theLocation = command - LOC_START;

    while (loc < len) // loop through all but the last
    {
        int c = (int)data[loc];
        loc++;
        gTotal[theLocation] = c;
        theLocation++;
    }
    gStatusFlag[getIndex(command)] = 1;
    gDisplayFlag[getIndex(command)] = 1;
}




