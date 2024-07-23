The most recent versions that are still able to reproduce the bug are
8.0.392.0, 11.0.21.0, 17.0.9.0. Since 8.0.402.0, 11.0.22.0, 17.0.10.0,
21.0.1.0, this bug has been fixed.

Fixed on Oct 25, 2023
https://github.com/eclipse/omr/pull/7155

Confirmed on Apr 24, 2023

Submitted on Apr 23, 2023
https://github.com/eclipse-openj9/openj9/issues/17249

===============
BELOW IS REPORT
===============

# Title
JDK 8/11/17/18 - incorrect result from JIT compilation due to erroneous loop condition evaluation

## System / OS / Java Runtime Information

### Java version
```
$ java -version

openjdk 17.0.6 2023-01-17
IBM Semeru Runtime Open Edition 17.0.6.0 (build 17.0.6+10)
Eclipse OpenJ9 VM 17.0.6.0 (build openj9-0.36.0, JRE 17 Linux amd64-64-Bit Compressed References 20230117_397 (JIT enabled, AOT enabled)
OpenJ9   - e68fb241f
OMR      - f491bbf6f
JCL      - 927b34f84c8 based on jdk-17.0.6+10)
```

### Operating system details
```
$ cat /etc/*release

DISTRIB_ID=Ubuntu
DISTRIB_RELEASE=20.04
DISTRIB_CODENAME=focal
DISTRIB_DESCRIPTION="Ubuntu 20.04.3 LTS"
NAME="Ubuntu"
VERSION="20.04.3 LTS (Focal Fossa)"
ID=ubuntu
ID_LIKE=debian
PRETTY_NAME="Ubuntu 20.04.3 LTS"
VERSION_ID="20.04"
HOME_URL="https://www.ubuntu.com/"
SUPPORT_URL="https://help.ubuntu.com/"
BUG_REPORT_URL="https://bugs.launchpad.net/ubuntu/"
PRIVACY_POLICY_URL="https://www.ubuntu.com/legal/terms-and-policies/privacy-policy"
VERSION_CODENAME=focal
UBUNTU_CODENAME=focal
```

```
$ uname -a

Linux luzhou 5.4.0-86-generic #97-Ubuntu SMP Fri Sep 17 19:19:40 UTC 2021 x86_64 x86_64 x86_64 GNU/Linux
```

## Description

Incorrect result is given when running the following
program. The bug affects 8u362, 11.0.18.0, 17.0.6.0,
18.0.2.1 using the hot and veryhot optlevels.

## Steps to reproduce
The following steps shows how to reproduce the bug on JDK 17.0.6.0 in a
Ubuntu Linux environment.

### Compile
```
$ javac C.java
```

### Execute
```
$ java -Xjit:optlevel=hot C

10142   // nondeterministic value
```

```
$ java -Xjit:optlevel=veryhot C

14331   // nondeterministic value
```

```
$ java -Xnojit C

0       // correct value
```

### Source code for an executable test case
```
// C.java

public class C {
    
    public static int m(int len) {
        int[] arr = new int[8];
        for (int i = 10000000, j = 0; (boolean) (i >= 1) && j < 100; i--, j++) {
            // should not enter inner loop.
            for (int k = 0; len < arr.length; ++k) {
                // throws ArithmeticException to break out.
                int x = 1 / 0;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        int sum = 0;
        
        for (int i = 0; i < 100_000; ++i) {
            try {
                m(13);
            } catch (ArithmeticException e) {
                sum += 1;
            }
        }

        System.out.println(sum); // expected 0
    }
}
```

### Workaround
Disable JIT.
```
$ java -Xnojit C
```
