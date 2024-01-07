#!/bin/bash
cd ../..
for file in dump/test/pht/all/*
do
  mkdir -p src/test/resources/test/pht/all/${file:18}/print
  cp -a $file/print/. src/test/resources/test/pht/all/${file:18}/print
done
for file in dump/test/siberia/all/*
do
  mkdir -p src/test/resources/test/pht/all/${file:18}/print
  cp -a $file/print/. src/test/resources/test/siberia/all/${file:21}/print
done