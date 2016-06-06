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
import buyer_api
import sellers_api
import orders_api
sys.path.append('%s/data/define' % CWD)
from errors import *

class Server(tornado.web.Application):
    def __init__(self):
        settings = dict(
            template_path=os.path.join(os.path.dirname(__file__), "templates"),
            static_path=os.path.join(os.path.dirname(__file__), "static"),
            cookie_secret='homebuy_seller_3.14159265',
            debug=True,
        )
        handlers = [
            (r"/", DefaultRequestHandler),
            (r"/text/(.*)", tornado.web.StaticFileHandler, {"path": "./static/text"}, ),
            (r"/seller", SellerRequestHandler),
            (r"/seller/commodities", SellerCommoditiesRequestHandler),
            (r"/seller/commodity", SellerCommodityRequestHandler),
            (r"/seller/barcodes", SellerBarcodesRequestHandler),
            (r"/seller/categories", SellerCategoriesRequestHandler),
            (r"/seller/category", SellerCategoryRequestHandler),
            (r"/buyer", BuyerRequestHandler),
            (r"/buyer/sellers", BuyerSellersRequestHandler),
            (r"/buyer/orders", BuyerOrdersRequestHandler),
            (r"/buyer/commodities", BuyerCommoditiesRequestHandler),
        ]
        tornado.web.Application.__init__(self, handlers, **settings)

class DefaultRequestHandler(tornado.web.RequestHandler):
    #@tornado.web.authenticated
    def get(self):
        self.write('Hello, world')

class SellerRequestHandler(tornado.web.RequestHandler):
    def get(self):
        seller_id = self.get_argument('id')
        resp = seller_api.instance.get(seller_id)
        self.write(resp)
     
    def post(self):
        seller = self.request.body
        resp = seller_api.instance.post(seller)
        self.write(resp)

class SellerCommoditiesRequestHandler(tornado.web.RequestHandler):
    def get(self):
        seller_id = self.get_argument('seller_id')
        if_modified_since = self.get_argument('if_modified_since', '0')
        check = self.get_argument('check', '0')
        resp = commodities_api.instance.get_seller_commodities(seller_id, if_modified_since, check)
        self.write(resp)
 
class SellerCommodityRequestHandler(tornado.web.RequestHandler):
    def post(self):
        commodity = self.request.body
        resp = commodity_api.instance.post(commodity)
        self.write(resp)
 
class SellerBarcodesRequestHandler(tornado.web.RequestHandler):
    def get(self):
        seller_id = self.get_argument('seller_id')
        if_modified_since = self.get_argument('if_modified_since', '0')
        check = self.get_argument('check', '0')
        resp = barcodes_api.instance.get_seller_barcodes(seller_id, if_modified_since, check)
        self.write(resp)
     
class SellerCategoriesRequestHandler(tornado.web.RequestHandler):
    def get(self):
        seller_id = self.get_argument('seller_id')
        if_modified_since = self.get_argument('if_modified_since', '0')
        check = self.get_argument('check', '0')
        resp = categories_api.instance.get_seller_categories(seller_id, if_modified_since, check)
        self.write(resp)

class SellerCategoryRequestHandler(tornado.web.RequestHandler):
    def post(self):
        category = self.request.body
        resp = category_api.instance.post(category)
        self.write(resp)

class BuyerRequestHandler(tornado.web.RequestHandler):
    def get(self):
        id = self.get_argument('id')
        resp = buyer_api.instance.get(id)
        self.write(resp)
     
    def post(self):
        buyer = self.request.body
        resp = buyer_api.instance.post(buyer)
        self.write(resp)

class BuyerCommoditiesRequestHandler(tornado.web.RequestHandler):
    def get(self):
        seller_id = self.get_argument('seller_id')
        if_modified_since = self.get_argument('if_modified_since', '0')
        check = self.get_argument('check', '0')
        resp = commodities_api.instance.get_seller_commodities(seller_id, if_modified_since, check)
        self.write(resp)
 
class BuyerSellersRequestHandler(tornado.web.RequestHandler):
    def get(self):
        area = self.get_argument('area')
        resp = sellers_api.instance.get_area_sellers(area)
        self.write(resp)

class BuyerOrdersRequestHandler(tornado.web.RequestHandler):
    def get(self):
        buyer_id = self.get_argument('buyer_id')
        resp = orders_api.instance.get_buyer_unfinished_orders(buyer_id)
        self.write(resp)

if __name__ == "__main__":
    server = tornado.httpserver.HTTPServer(Server())
    server.listen(80)
    tornado.ioloop.IOLoop.instance().start()

