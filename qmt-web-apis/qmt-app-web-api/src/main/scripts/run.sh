#!/bin/bash

SCRIPT_HOME=$(dirname $(readlink -f $0))
echo '------------------------------'
bash -ex $SCRIPT_HOME/server_ctl.sh restart -p 9132 -m SmsAppApiServer