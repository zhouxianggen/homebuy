# -*- coding: utf-8 -*-
#!/usr/bin/env python

__auth__ = "xianggen.zhou"
__date__ = "2016-03-23"
__info__ = "server for homebuy"

import os, sys
import tornado.httpserver
import tornado.ioloop
import tornado.web
import tornado.template
CWD = os.path.dirname(os.path.abspath(__file__))
sys.path.append('%s/data/api' % CWD)
import commodity_api
import commodities_api
import seller_api
import barcodes_api
import categories_api
import category_api
import image_api
sys.path.append('%s/data/define' % CWD)
from errors import *

class SellerServer(tornado.web.Application):
    def __init__(self):
        settings = dict(
            template_path=os.path.join(os.path.dirname(__file__), "templates"),
            static_path=os.path.join(os.path.dirname(__file__), "static"),
            cookie_secret='homebuy_seller_3.14159265',
            debug=True,
        )
        handlers = [
            (r"/", DefaultRequestHandler),
            (r"/commodities", CommoditiesRequestHandler),
            (r"/commodity", CommodityRequestHandler),
            (r"/seller", SellerRequestHandler),
            (r"/barcodes", BarcodesRequestHandler),
            (r"/categories", CategoriesRequestHandler),
            (r"/category", CategoryRequestHandler),
            (r"/image", ImageRequestHandler)
        ]
        tornado.web.Application.__init__(self, handlers, **settings)

class DefaultRequestHandler(tornado.web.RequestHandler):
    #@tornado.web.authenticated
    def get(self):
        self.write('Hello, world')

class CommoditiesRequestHandler(tornado.web.RequestHandler):
    def get(self):
        seller_id = self.get_argument('seller_id')
        if_modified_since = self.get_argument('if_modified_since', '0')
        count = self.get_argument('count', '100')
        resp = commodities_api.instance.get(seller_id, if_modified_since, count)
        self.write(resp)
 
class CommodityRequestHandler(tornado.web.RequestHandler):
    def post(self):
        commodity = self.request.body
        resp = commodity_api.instance.post(commodity)
        self.write(resp)
    
class SellerRequestHandler(tornado.web.RequestHandler):
    def get(self):
        seller_id = self.get_argument('id')
        resp = seller_api.instance.get(seller_id)
        self.write(resp)
     
    def post(self):
        seller = self.request.body
        resp = seller_api.instance.post(seller)
        self.write(resp)
 
class BarcodesRequestHandler(tornado.web.RequestHandler):
    def get(self):
        seller_id = self.get_argument('seller_id')
        if_modified_since = self.get_argument('if_modified_since', '0')
        count = self.get_argument('count', '100')
        resp = barcode_api.instance.get(seller_id, if_modified_since, count)
        self.write(resp)
     
class CategoriesRequestHandler(tornado.web.RequestHandler):
    def get(self):
        seller_id = self.get_argument('seller_id')
        if_modified_since = self.get_argument('if_modified_since', '0')
        count = self.get_argument('count', '100')
        print 'CategoriesRequestHandler: [%s][%s][%s]' % (seller_id, if_modified_since, count)
        resp = categories_api.instance.get(seller_id, if_modified_since, count)
        self.write(resp)

class CategoryRequestHandler(tornado.web.RequestHandler):
    def post(self):
        category = self.request.body
        resp = category_api.instance.post(category)
        self.write(resp)
      
class ImageRequestHandler(tornado.web.RequestHandler):
    def post(self):
        image = self.request.body
        resp = image_api.instance.post(image)
        self.write(resp)

if __name__ == "__main__":
    server = tornado.httpserver.HTTPServer(SellerServer())
    server.listen(80)
    tornado.ioloop.IOLoop.instance().start()

