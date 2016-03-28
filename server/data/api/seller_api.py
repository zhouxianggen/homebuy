# -*- coding: utf-8 -*-
#!/usr/bin/env python

__author__  = "xianggen.zhou"
__date__    = "2016-03-23"

import os, sys
from single_resource_api import SingleResourceApi
CWD = os.path.dirname(os.path.abspath(__file__))
sys.path.append('%s/../define' % CWD)
from table_schema import *

class SellerApi(SingleResourceApi):
    
    def __init__(self):
        self.entry = SellerEntry

instance = SellerApi()
