#!/bin/bash
cd ../..
for file in src/test/resources/test/*/all/*/unparse
do
  rm -rf $file
done
