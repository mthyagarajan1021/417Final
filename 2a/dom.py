#!/usr/bin/python3

import numpy #import numpy for getting random numbers for the rows
import sys #import sys for standard input

'''
norm of number using max and min of columns and the number itself
'''
def numNorm(maximum, minimum, number):
    return (number - minimum) / (maximum - minimum) #formula to calculate the norm of the number

'''
computation of dom score given two rows
'''
def dom(header, r1, r2):
    s1, s2 = 0, 0 #variables used for computation 
    cs = [] #list of indices of headers
    ws = [] #list of weights of headers
    for each in header:
        if each[2] != 0:
            cs.append(each[1]) #get only those indices which are non  0
            ws.append(each[2]) #get only those weights whos indices are non 0
    for c, w in zip(cs, ws):
        a0 = float(r1[c]) #get each element of row1 which has weights 
        b0 = float(r2[c]) #get each element of row2 which has weights 
        a  = numNorm(header[c][3], header[c][4], a0) #get norm of a0 using min, max and a0
        b  = numNorm(header[c][3], header[c][4], b0) #get norm of b0 using min, max and b0
        s1 = s1 - 10**(w * (a-b)/len(cs)) #computation of s1
        s2 = s2 - 10**(w * (b-a)/len(ws)) #computation of s2
    
    return s1/len(cs) < s2/len(cs) #return if row1 is dominant in comparison to row2

'''
prints dom scores of each row
'''
def doms(header, rows):
    sample_rows = 100 #sample row hard coded to be 100
    header_names = [] #names of all headers
    for each in header:
        header_names.append(each[0]) #get names of all headers
    header_names.append('>dom') #add another header for the file
    print(','.join(header_names)) #print all headers to console output
    body = []
    for i in range(len(rows)):
        current = rows[i] #get current row
        dom_score = 0 #dom score is temporarily 0
        list_of_others = numpy.random.choice(len(rows), 101, replace=False).tolist() #list of other rows to choose from 
        if i in list_of_others:
            list_of_others.remove(i) #if list_of_others contains i, then remove i
        else:
            list_of_others.pop(0) #else remove first value
        for j in list_of_others:
            another = rows[j] #get random row to compare ith row with 
            if dom(header, current, another): #if dom returns true
                dom_score += 1.0 / sample_rows #computation of dom score 
        current.append(str(round(dom_score, 2))) #append dom score of current row to current row's array
        body.append(current) #print current row 
    body.sort(key=lambda x:x[-1]) #sort rows using the dom score 
    for each in body:
        print(','.join(each)) #print rows to console output
    
'''
calculates max and min of a column using all rows and index
'''
def max_and_min(rows, i):
    minimum = float('inf') #minimum of each column
    maximum = float('-inf') #maximum of each column
    for each in rows:
        if each[i].isdigit():
            num = int(each[i]) * 1.0 #change int to float if number processed is an int and not a float
        if num > maximum:
            maximum = num #replace max with actual maximum
        if num < minimum:
            minimum = num #replace min with actual minimum
    return maximum, minimum #return max and min of column
                
'''
main method. Gets data file to read from and prints all dom scores
'''
def main():
    header_names = [] #names of all headers
    header = [] #['name', 'c', 'w', 'max', 'min']
    rows = [] #array of rows
    file = open("run.out", "w")
  
    header_names = sys.stdin.readline().rstrip().split(',') #first row of data file is the header file
    for line in sys.stdin.readlines():
        rows.append(line.rstrip().split(',')) #each row is read and added to the rows array
    
    for i in range(len(header_names)):
        each_header_info = [] #list for header dictionary
        maximum = float('inf') #maximum of each column
        minimum = float('-inf') #minimum of each column
        each_header_info.append(header_names[i]) #list has header name now
        each_header_info.append(i) #list has index of header name now
        if '<' in header_names[i]:
            each_header_info.append(-1) #list has weight of header now 
            maximum, minimum = max_and_min(rows, i) #max and min of this column
        elif '>' in header_names[i]:
            each_header_info.append(1) #list has weight of header now 
            maximum, minimum = max_and_min(rows, i) #max and min of this column
        else:
            each_header_info.append(0) #list has weight of header now 
            maximum, minimum = 0, 0 #max and min are 0 for row without weight
        each_header_info.append(maximum) #list has max of that column
        each_header_info.append(minimum) #list has min of that column
        header.append(each_header_info) #header array now has information about each header
    file.write('Input: \n') #write input to run.out
    file.write(','.join(header_names)) #write header names to run.out
    file.write('\n') #write new line to run.out
    for each in rows:
        file.write(','.join(each)) #write each input row to run.out
        file.write('\n') #write new line to run.out
    file.write('\n') #write new line to run.out
    file.write('\n') #write new line to run.out
    file.write('Output: \n') #write output to run.out
    file.close() #close run.out
    doms(header, rows) #prints all dom scores
    
if __name__ == "__main__":
    main() #run main first.
