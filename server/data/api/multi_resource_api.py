# -*- coding: utf-8 -*-
#!/usr/bin/env python

__author__  = "xianggen.zhou"
__date__    = "2016-03-23"

import os, sys
import json
import time
from mysql_api import mysql
CWD = os.path.dirname(os.path.abspath(__file__))
sys.path.append('%s/../define' % CWD)
from errors import *

class MultiResourceApi(object):
    
    def __init__(self):
        self.entry = None

