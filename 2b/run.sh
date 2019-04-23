#!/bin/bash
echo "Input:" > run.out
echo "-n 2000 -s 1729 -v False" >> run.out
echo "" >> run.out
echo "" >> run.out
echo "Output:" >> run.out
./MonteCarlo.java -n 2000 -s 1729 -v False >> run.out