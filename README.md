# Shoe Stores

##### _Java Advanced Database Code Review for Epicodus, 4 September 2015_

#### By **Molly Waggett**

## Description

This app allows a user to add shoe stores and brands to a database. The homepage
displays lists of stores and brands along with links to add new stores and
brands. Clicking on a store takes the user to a list of the brands that store
carries. From this page, the user may add brands to the store, edit the store's
name, or delete the store. Clicking on a brand takes the user to a list of the
stores that carry that brand. From this page, the user may add stores to the brand.

## Setup

* Set up the database in PostgreSQL by running the following commands in your terminal:
```
  psql
  CREATE DATABASE shoe_stores;
  \c shoe_stores;
  CREATE TABLE stores (id serial PRIMARY KEY, name varchar);
  CREATE TABLE brands (id serial PRIMARY KEY, name varchar);
  CREATE TABLE stores_brands (id serial PRIMARY KEY, store_id int, brand_id int);
```
* If you wish to run tests, create a test database:
```
  CREATE DATABASE shoe_stores_test WITH TEMPLATE shoe_stores;
```
* Clone this repository.
* Using the command line, navigate to the top level of the cloned directory.
* Make sure you have gradle installed. Then run the following command in your terminal:
```
  gradle run
```
* Go to localhost:4567.
* Go!

## Technologies Used

* Java
* PostgreSQL
* Spark
* Velocity
* Gradle
* JUnit
* FluentLenium

### Legal

Copyright (c) 2015 **Molly Waggett**

This software is licensed under the MIT license.

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
