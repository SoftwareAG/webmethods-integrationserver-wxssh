# webmethods-integrationserver-wxssh
webMethods Integration Server package to execute commands via SSH on remote machines



## Development
- Prerequisite: You need "Local Service Development" installed (located in Designer preferences at  "Software AG / Service Development / Local Service Development")
- Get sources
  - Via Software AG Designer (no separate Git installation needed)
    - Open "Java" perspective
	- Click "Import projects"
	- Select "Git / Projects from Git"
	- Select "Clone URI"
	- Paste Git URI from green "Clone or download" button above
	- Adjust target directory to `<WORKSPACE>/WxSSH`
	- Confirm defaults on all further dialogues and finish the import
  - Via command line (requires local Git installation)
    - Go into Designer workspace (e.g. `/home/john/workspace105`)
    - Clone Git repository into new directory `git clone https://github.com/SoftwareAG/webmethods-integrationserver-wxssh.git WxSSH`
	- Import as existing project into workspace
- Activate package in Integration Server
	- If the "Service Development" perspective has not been active before you openend the "Java" perspective, you must quickly switch there and then directly back to "Java". This is needed to initialize the Local Service Development feature.
	- In the "Java" perspective right-click the project name and select "Move Project to IS Package"
	- Switch to the "Service Development" perspective and the WxSSH package should show up
	
	

______________________
These tools are provided as-is and without warranty or support. They do not constitute part of the Software AG product suite. Users are free to use, fork and modify them, subject to the license agreement. While Software AG welcomes contributions, we cannot guarantee to include every contribution in the master project.

Contact us at [TECHcommunity](mailto:technologycommunity@softwareag.com?subject=Github/SoftwareAG) if you have any questions.
