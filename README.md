# CS320
Projects for CS320: Software Testing, Automation QA

How can I ensure that my code, program, or software is functional and secure?
This course asked us to create software for a hypothetical customer using tried and true testing methods with JUnit.
The software itself needed a Contact class, Contact Service class, Task class, Task Service Class, Appointment Class,
and Appointment Service class. Each of these classes had a list of requirements we had to meet when developing our
code, and though the list was short, the testing required was rigorous.

To ensure my code was functional and secure, I followed all of the previous best practices I have learned throughout
my academic career at SNHU: I followed standard naming conventions for the scripting language I used, I organized all
elements of my classes appropriately, and I left thorough commentary throughout my code so anyone looking at it would
be able to figure out what it was doing and why. I also adhered to good testing standards like the single responsibility
principle, and used black-box testing techniques like input partitioning and boundary analysis to inform how I should 
write my JUnit tests (**NOTE:** Unit tests, by definition, technically fall under the umbrella of white-box testing 
because each test focused on individual components/methods in isolation, each test was written to meet a general goal 
of code coverage, and I wrote the source code being tested, so I had knowledge of how it worked, all of which are 
aspects of white box testing).

To interpret user needs and incorporate them into my programs, I combed through our project rubric many times to make
sure each requirement was being met by my code, and through my testing methods, confirmed those requirements were
working as intended. My classes have restrictions dictated by the customer requirements in our rubric, and those
restrictions throw illegal argument exceptions with unique messages when any of them are not met.

Throughout this entire course, I approached my software development with an air of caution and an adherence to quality.
Testing relies on a certain meticulousness and a sense of detachment from your own work to eliminate any bias; it was
very difficult to do, but I spent far more time writing the tests for my software than writing the software itself to
ensure I was not missing anything. Even now I am not certain I covered all possible errors, and I am sure that an
experienced Software QA Tester would be able to pick apart the work I have done, but I am satisfied with the result
knowing this was my first attempt at this practice as a student. Through peer review and further practice, I am sure
I will be able to produce even higher quality code and tests. 
