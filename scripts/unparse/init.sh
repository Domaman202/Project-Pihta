#!/bin/bash
cd ../..
for file in src/test/resources/test/pht/all/*
do
  mkdir -p $file/unparse/parsed
  echo "(module
  (name \"${file:19}/unparse/parsed\")
  (version \"1.0.0\")
  (author \"DomamaN202\")
  (deps [\"pht\"])
  (src [\"unparse.pht\"]))" > $file/unparse/parsed/module.pht
  mkdir -p $file/unparse/processed
  echo "(module
  (name \"${file:19}/unparse/processed\")
  (version \"1.0.0\")
  (author \"DomamaN202\")
  (deps [\"pht\"])
  (src [\"unparse.pht\"]))" > $file/unparse/processed/module.pht
done
for file in src/test/resources/test/siberia/all/*
do
  mkdir -p $file/unparse/parsed
  echo "(module
  (name \"${file:19}/unparse/parsed\")
  (version \"1.0.0\")
  (author \"DomamaN202\")
  (deps [\"pht\"])
  (src [\"unparse.pht\"]))" > $file/unparse/parsed/module.pht
  mkdir -p $file/unparse/processed
  echo "(module
  (name \"${file:19}/unparse/processed\")
  (version \"1.0.0\")
  (author \"DomamaN202\")
  (deps [\"pht\"])
  (src [\"unparse.pht\"]))" > $file/unparse/processed/module.pht
done
