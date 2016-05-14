*Disclaimer: This document is written in the style of a poorly-worded Corporate Intro & How-To document. To that end it is meant to be funny, but still accurately describe all the inner workings of this program/site. Please note that any similarities of existing companies is purely by coincidence. Also, the over-arching idea is that this is good tech, but the manual may not have been written by the right person.*

# MyCX Media INTERNAL USE ONLY
  *Where there's an "I" in "Media" and also "Me"*


## Welcome to MyCX Media
### About Us
We are all about Sharing at MyCX Media. After you've been here for a little while, I'm certain you'll have caught the bug and fit right in. DO NOT LET THIS DOCUMENT FALL OUT OF YOUR POSSESSION. To foster our sharing nature we require all personal details be posted where all company employees can see. We used to have spreadsheets in the Restrooms, but now we have a website to serve that purpose.

## Technology Stack
 * Backend (this is the stuff they keep locked in that freezing cold room)
   * Clojure, including but not limited to
     * Ring Server with various middleware
     * Compujure for routing
     * Incanter for data analysis
     * Cheshire for JSON conversion
 * Frontend (this involves our user experience)
     * Hiccup for html/css rendering
     * Haversine for lat/lon calculations
     * JSON-HTML package for rendering JSON as HTML


## Operational Discussion
### Also known as the IT department's continual employment agreement

1. Once you have received your punch card, please download the software onto your office computer (DO NOT run on personal computation devices). I was told you could "clone" it as well, but that seems a bit too risky for just a simple media company.
2. You can find it on the Internet at https://github.com/randre03/engineeringtest
3. Once the file is downloaded/cloned you can run it as a website by navigating to the mycx directory and typing the following incantation at the terminal which I believe comes from sanscrit `lein ring server`
4. This should automatically open a webpage in your default browser to http://localhost:3000.
5. You should be greeted warmly by our slogan. In case you can't tell, that's how the kids say "Hello" these days.
6. Remember, this technology and anything on the site is for INTERNAL-USE ONLY.

### Using the technologies described above, once the Internal website has been loaded, I have no doubt you'll be intimidated by the plethora of what you can accomplish with it. More Specifically
- localhost:3000/ - This is the homepage as already described above. We're thinking about expanding it in the future, but even still, there is a lot to take in.

- localhost:3000/users - This is a list of all our employees, their ages and precisely where they live. Don't worry, we'll get you listed on here soon. Yes, this is mandatory.

- localhost:3000/users/:id - This allows you to focus on any one particular employee's information in beautiful formatting.

- localhost3000/age?min_age=min&max_age=max - By visiting this page, and entering the minimum age and maximum age-range, you'll be able to find a list of our employees who fit those age criteria. Very helpful for those of you out on the dating scene.

- localhost:3000/users/loc?lat=lat&lon=lon - Again, another helpful website page for those of you who are dating (or who love carpooling), just simply enter where you live in latitude and longitude and this will provide names and lat/long for those employees that live within 5 miles of you. No stalkers please.


## FREQUENTLY ASKED QUESTIONS

### What if there is a problem accessing something from then web interface?

There might be times where it is a problem accessing a part of the site. The most technical are the last two routes described above, so our tech guys provided the following work around:

1. Open up your REPL (and to save some keystrokes) enter the mycx namespace and make sure the data (found in the root directory of your download in a directory called 'data') is loaded, too.

2. To get a list of employees (called 'users' in the code) within your defined age range enter the following at the REPL `(users-by-age min max)` and this will result in a table at the REPL of employees in that age range.

3. To get a list of employees who are within a certain lat/long, perform step 1 directly above and then enter `(user-radius lat long)` at the REPL. This will print to the REPL the names and locations of any employees that are within 5 miles of the location you provided.

### Will this be accessible in the bathroom like the old spreadsheets were?

We are currently testing a design out in the Executive Washroom. Further testing is needed.

### What is the strange formatting that the website outputs data in?

That format is called JSON. According to IT we are moving toward outputting our information using standardized formats.
