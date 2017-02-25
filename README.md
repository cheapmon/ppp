# Privacy Policy Picker (P<sup>3</sup>)

__About__
-

P<sup>3</sup> is a simple tool visualizing how privacy policies of certain websites have changed over time.
20 websites are crawled everyday, differing version are stored in a database and are visualized on a responsive website.

P<sup>3</sup> uses either the websites own archive or archive.org to identify older policy versions.

This tool was developed as a study project for the natural language processing group at Leipzig University, Germany.

__Requirements__
-

A webserver with the following dependencies:

Frontend:
- NoseJS (Npm/Bower),
- Bootstrap,
- vis,
- diff (Npm) or jsdiff (Bower),
- jQuery.

Backend:
- Java,
- SQLite3,
- Maven.

Extraction:
- Ruby,
- Ruby gems
	- date, 
	- open-uri,
	- open_uri_redirections,
	- nokogiri,
	- rubygems,
	- sqlite3,
	- json,
	- differ.


__Usage__
-

__Installation__
- Init the database:
	
    - `cd` to `ppp_extraction/src`.
    - Set the `@db_path` in `PrivacyExtractor.rb`.
    - Run `ruby init.rb`.  

The database is created at the specified path and by default is called `policies.db`.
- Build the rest service:

	- `cd` to `ppp_frontend/app/src`.
	- Specify the rest service IP at `index_directive.js`.
	- `cd` to `ppp_backend/src/main/java/server_core`.
	- Specify the rest service IP at `ServerStarter.java`.
	- Specify the crawling time at `CrawlerStarter.java`.
	- `cd` to `ppp_backend`.
	- Run `mvn clean package`.

The server jar is created at `ppp_backend/target` and by default is called `ppp_server-1.0-jar-with-dependencies.jar`.

__Run__

Start the rest service:
- `cd` to `ppp_backend/target`.
- Run `java -jar ppp_server-1.0-jar-with-dependencies.jar`.

The service is running and crawling until stopped by hitting the spacebar twice.

Open a web browser at your specified IP to see the frontend in action.

__Database structure__

Column | Meaning
--- | ---
ID | Primary key.
SYSTEM_DATE | Date on which the privacy policy was in the current state.
DISPLAY_DATE | The same date in a reader friendly format.
LINK | Hyperlink to find the extracted version.
CONTENT | The extracted plaintext.

__Crawl a single website__

- `cd` to `ppp_extraction/src`.
- If the website hasn't been crawled before, run `ruby crawl.rb _website_ fetch`.
- Otherwise, run `ruby crawl.rb _website_ update`.

__Adding a website__

- `cd` to `ppp_frontend/app/src`.
- Add the site name at `index_directive.js`. (e.g. "Google")
- `cd` to `ppp_backend/src/main/java/server_core`.
- Add the site name at `CrawlerStarter.java`. (e.g. "google")
- `cd` to `ppp_extraction/src`.
- Add the site name at `init.rb`. (e.g. "google")
- Add settings for crawling at `Crawler.rb`. Common options:
	- _.own_archive_: Whether the website has its own archive or not.
	- _.url_: The link to the website where the privacy policy is present.
	- _.table_name_: The name of the database table in which the policy versions are stored. (e.g. "GOOGLE")
    - _.xpath_: A xpath for the html container including the privacy policy.
    
    Additional options can be found in the documentation for `PrivacyExtractor.rb`.
 - `cd` to `ppp_extraction/src`.
- Run `ruby crawl.rb _website_ fetch`.
    
Mind the naming conventions!

__Copyright__
-

P<sup>3</sup> was developed by Alexander Prull, Jörn-Henning Daug and Simon Kaleschke and is published under the [3-clause BSD-License](https://opensource.org/licenses/BSD-3-Clause).

---
For questions and issues message sk97mixu@studserv.uni-leipzig.de.