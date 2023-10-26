# RAFT 2.0

<a href="https://www.robosoftin.com/">![Robosoft Logo](https://www.robosoftin.com/assets/img/trademarks/rst_long_logo.jpg)</a>

## Setup

* Clone the repo
* Install dependencies `mvn compile`

## Running your tests

### WEB
- To run tests, run `mvn test -f generic-web`
- To run with arguments, run `mvn test -f generic-web -Denvironment=stage -Dbrowser=chrome -Drunmode_browser=local`
- Refer the configuration file to know about the arguments `generic-web/src/test/resources/config/config.properties`

### MOBILE
- To run tests, run `mvn test -f generic-mobile`
- To run with arguments, run `mvn test -f generic-mobile -Denvironment=stage -Dplatform=ios -Drunmode_mobile=remote`
- Refer the configuration file to know about the arguments `generic-mobile/src/test/resources/config/config.properties`

### API
- To run tests, run `mvn test -f generic-api`
- To run with arguments, run `mvn test -f generic-api -Denvironment=stage`
- Refer the configuration file to know about the arguments `generic-api/src/test/resources/config/config.properties`



