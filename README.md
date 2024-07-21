# LeJit

LeJit is a framework that automates the extraction of templates 
from open-source Java projects, which are needed by JAttack to test 
compilers. JAttack, automated compiler testing through differential 
analysis of execution results of different compilers
(Oracle Hotspot, Oracle GraalVM and IBM OpenJ9), on programs generated 
from handwritten templates. LeJit removes this manual step and 
automates the compiler testing flow end-to-end.

## Table of contents

1. [Hall of Fame](#Hall-of-Fame)
2. [Citation](#Citation)
3. [Contact](#Contact)


## Hall of Fame


|JVM|Bug ID|
|:---|:---|
|GraalVM|GR-45498|
|HotSpot|JDK-8239244/CVE-2020-14792|
|	|JDK-8258981|
|	|JDK-8271130/CVE-2022-21305|
|	|JDK-8271276|
|	|JDK-8271459|
|	|JDK-8271926|
|	|JDK-8297730|
|	|JDK-8301663|
|	|JDK-8303946|
|	|JDK-8304336/CVE-2023-22044|
|	|JDK-8305946/CVE-2023-22045|
|OpenJ9|17066|
|	|17129|
|	|17139|
|	|17171|
|	|17212|
|	|17249|
|	|17250|

## Citation

If you use LeJit in your research, please cite our
[FSE'24 paper](https://dl.acm.org/doi/abs/10.1145/3643777).

```bibtex
@article{zang24lejit,
  author = {Zang, Zhiqiang and Yu, Fu-Yao and Thimmaiah, Aditya and Shi, August and Gligoric, Milos},
  title = {Java JIT Testing with Template Extraction},
  booktitle = {ACM International Conference on the Foundations of Software Engineering},
  year = {2024},
  pages = {1129--1151},
  doi = {10.1145/3643777},
}
```

## Contact

Let me ([Zhiqiang Zang](https://github.com/CptGit)) know if you have
any questions.
