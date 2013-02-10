#!/bin/bash
cd $(dirname $0)
mkdir -p db/commitlog
mkdir -p db/data
mkdir -p db/log
mkdir -p db/saved_caches
bin/cassandra -f
