# studentdatacontract


## Quickstart

[Java 8](or higher) is required.  

#### 1. **Clone this git repo:**

```bash
git clone https://github.com/jack9684/studentdatacontract.git
```

#### 2. **Go to the project directory:**

```bash
cd studentdatacontract
```

#### 3. **Compile the smart contract:**

```bash
./gradlew neow3jCompile
or
gradlew neow3jCompile
```

#### 4. **You will see the following output in the directory `./build/neow3j`:**

```bash
NEF file: StuNFTToken.nef
Manifest file: tuNFTToken.manifest.json
Debug info zip file: StuNFTToken.nefdbgnfo
```

#### 5. **Deploy the contract**
set the private key at com.cj.studentcontract.Deployment 
```bash
private static final String Jack_PKEY = "YOUR PRIVATE KEY";
 ```
set the owner of contract at
```bash
package com.cj.studentcontract.StuNFTToken 
static final Hash160 contractOwner = StringLiteralHelper.addressToScriptHash("xxxxxxxxxxxxx");

  ```
- Run a local [Neo Express](https://github.com/neo-project/neo-express) instance. The project 
  includes a Neo Express configuration file.
- Fund cj's account: `neoxp transfer 100 GAS genesis cj`
- Go to the `com.cj.studentcontract.Deployment` class and run it.

