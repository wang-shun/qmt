#!/bin/bash

SCRIPT_HOME=$(dirname $(readlink -f $0))
PHOME=$(dirname $SCRIPT_HOME)
cp -rf /letv/www/lesports-msite-web/output/* $PHOME/war
bash -ex $SCRIPT_HOME/server_ctl.sh restart -p 9860 -m MSiteWebServer