### Overview
This repository is the engineering test for ICX Media engineering candidates.
This readme will provide you with all the instructions necessary to complete the
test, i.e prerequisites for the project, how to ask clarifying questions,
complete the test, and submit the test to ICX.

### The Problem
In the languages listed below, take the .csv file in the data/ directory and
productionize a webservice to read in the CSV file and output it in JSON.
Furthermore, once the data is in a JSON format, present the data in a creative
way to the end-user.

### The Evaluation
We look at the test based on the following criteria (in no particular order)
- The ability to follow instructions
- Understanding of source control systems
- Quality of code submission

#### Back-end focused Engineer
- In the language of your choice from the languages listed below, take
the provided CSV and create HTTP endpoints that return the following:
 * a user entry, given an ID as a path param, e.g. ```http://<yourapp>/users/user/1```
 * a list of users by age range given ```min_age``` and ```max_age```
 query paramaters, e.g. ```http://<yourapp>/users/age?min_age=22&max_age=65```

##### EXTRA CREDIT
- a list of users within 5 mile radius of a given a lat,long coordinate,
e.g. ```http://<yourapp>/users/loc?lat=38.900918&long=-77.035857```

All results should be returned as JSON

#### Front-end focused Engineer
- Present data from the CSV in a usable form to a user

### Exceed Expectations
- Create a service and a front-end to present the data.
- For front-end developers, mapping out locations using a JS map library such as
Google Maps or Leaflet.

### Prerequisites
Although, we are flexible on the languages for this test, we have limited the
amount of languages as to quicken the review process.

#### Languages
- Java
- Ruby
- Clojure
- Python
- JavaScript
- HTML

#### Notables
- Use of front-end JavaScript libraries, such as Bootstrap and jQuery, are
strongly discouraged
- Several factors play into the evaluation of your submission, design, quality,
completeness, timeliness
- All levels of skill and experience are given this test, please take the
exercise as far as you can and submit
- No submission will be the same, so be perpared to walk us through all the
aspects that contribute to your submission's evaluation (see above)

### Questions?
Review any of the Github issues to see if your question has already been asked
and answered.  If it has not been asked, submit a Github issue in this project
and someone will answer your question as soon as possible.

### Submission
Fork this repository and email the link to your forked repository to
engineering@icxmedia.com.  Please put your full name in the subject line.  In
addition, please provide a file in the repository named INSTRUCTIONS_README.md
file that provides instructions on how to run your program.
