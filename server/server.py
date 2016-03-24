# -*- coding: utf-8 -*-
#!/usr/bin/env python

__auth__ = "xianggen.zhou"
__date__ = "2016-03-23"
__info__ = "server for homebuy"

import tornado.httpserver
import tornado.ioloop
import tornado.web
import tornado.template
CWD = os.path.dirname(os.path.abspath(__file__))
sys.path.append('%s/api' % CWD)
from commodity_api import CommodityApi
from commodities_api import CommoditiesApi
from seller_api import SellerApi
from barcodes_api import BarcodesApi
from categories_api import CategoriesApi
from category_api import CategoryApi
from image_api import ImageApi

class HomeBuyServer(tornado.web.Application):
    def __init__(self):
        settings = dict(
            template_path=os.path.join(os.path.dirname(__file__), "templates"),
            static_path=os.path.join(os.path.dirname(__file__), "static"),
            cookie_secret='homebuy_3.14159265',
            debug=True,
        )
        handlers = [
            (r"/", DefaultRequestHandler),
            (r"/commodities", CommoditiesRequestHandler),
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
		if_modified_since = self.get_argument('if_modified_since')
		resp = CommoditiesApi.get(seller_id, if_modified_since)
        self.write(resp)
	
	def post(self):
		seller_id = self.get_argument('seller_id')
		commodities = self.request.body
		resp = CommoditiesApi.post(seller_id, commodities)
		self.write(resp)
 
class SellerRequestHandler(tornado.web.RequestHandler):
    def get(self):
		seller_id = self.get_argument('seller_id')
		resp = SellerApi.get(seller_id)
		self.write(resp)
     
    def post(self):
        seller = self.request.body
		resp = SellerApi.post(seller)
		self.write(resp)
 
class BarcodesRequestHandler(tornado.web.RequestHandler):
	def get(self):
		seller_id = self.get_argument('seller_id')
		if_modified_since = self.get_argument('if_modified_since')
		resp = BarcodesApi.get(seller_id, if_modified_since)
		self.write(resp)
     
class CategoriesRequestHandler(tornado.web.RequestHandler):
    def get(self):
		seller_id = self.get_argument('seller_id')
		if_modified_since = self.get_argument('if_modified_since')
		resp = CategoriesApi.get(seller_id, if_modified_since)
		self.write(resp)

class CategoryRequestHandler(tornado.web.RequestHandler):
    def post(self):
        category = self.request.body
		resp = CategoryApi.post(category)
		self.write(resp)
      
class ImageRequestHandler(tornado.web.RequestHandler):
    def post(self):
        image = self.request.body
		resp = ImageApi.post(image)
		self.write(resp)

if __name__ == "__main__":
    server = tornado.httpserver.HTTPServer(HomeBuyServer())
    server.listen(80)
    tornado.ioloop.IOLoop.instance().start()
