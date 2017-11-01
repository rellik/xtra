# xtra

Start the util with com.donnybiddappa.socgen.Main {absolute path to input file}

Sample input file can be found here /src/test/java/input.txt

You can run all tests using IDE no test suite is created.  

Documentation for code is absolutely minimal and descriptive Classes and methods were preferred 


Assumptions:

1. The brand, category and discount information to be hardcoded.
2. Processing of input file not very important... therefore code test coverage is 0% for input file processig.
3. Category heirarchy assumed to be in the following structure.
       
       Category discounts:
       
       Men's wear
           |- Shirts
           |- Trousers
               |- Casuals (30% off)
               |- Jeans   (20% off)
    
       Women's wear (50% off)
           |- Dresses
               |- Footwear
    
