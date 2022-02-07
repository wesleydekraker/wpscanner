# WordPress Security Scanner

Tool to scan WordPress websites for security vulnerabilities. It scans for vulnerable themes/plugins and reports them to the site owner. It can also detect the version of the WordPress installation and whether the "All In One WP Firewall" is active.

Analysis is performed using black box testing and thus does not require a username or password. The tool is a web application written in Java using the Spring framework.

## Requirements

Install the following requirements:

* Java 17
* Maven

## Demo

Screenshot after analyzing example.com. It shows the security problems discovered and the installed plugins/themes.

![screenshot](https://github.com/wesleydekraker/wpscanner/blob/master/doc/wpscanner.png?raw=true)

Screenshot from the admin area. It lists the analyzed websites and known vulnerabilities. If new vulnerabilities are discovered, they can be added to the scanner.

![screenshot-admin-area](https://github.com/wesleydekraker/wpscanner/blob/master/doc/wpscanner-admin-area.png?raw=true)

## Disclaimer

This repository is not designed for malicious use. The purpose is to help people learn more about software security.

