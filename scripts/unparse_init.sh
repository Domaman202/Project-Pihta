#!/bin/bash
for file in ../src/test/resources/test/*/all/*
do
  mkdir -p $file/unparse/parsed
  echo "(module
  (name \"${file:22}/unparse/parsed\")
  (version \"1.0.0\")
  (author \"DomamaN202\")
  (deps [\"pht\"])
  (files [\"unparse.pht\"]))" > $file/unparse/parsed/module.pht
  mkdir -p $file/unparse/processed
  echo "(module
  (name \"${file:22}/unparse/processed\")
  (version \"1.0.0\")
  (author \"DomamaN202\")
  (deps [\"pht\"])
  (files [\"unparse.pht\"]))" > $file/unparse/processed/module.pht
done
