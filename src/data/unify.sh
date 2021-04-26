#!/bin/bash

# This script is intend to unify lines in the files in current directory

for fl in ./*.txt;
do
	echo "$fl: "
	wc -l $fl
	cat $fl | sort -u | wc -l
	cat $fl | sort -u | tee $fl 1> /dev/null
done