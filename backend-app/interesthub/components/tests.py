from django.test import TestCase
from .models import *
from .serializers import *

class ComponentTests(TestCase):

	def setUp(self):
		self.comp_attr = {
			"compotent_type": "text",
			"order": 2
		}

		self.comp = Component.objects.create(order=2)
		self.serializer = ComponentSerializer(instance=self.comp, data={"order":2})

	def test_text_component(self):
		self.assertTrue(self.serializer.is_valid())
		# self.assertEqual(set(serializer.errors), set(['order']))  Hata çıkan "key"e bakılır
		data = self.serializer.data
		self.assertEqual(set(data.keys()), set(["id", "component_type", "order"]))
		#self.assertEqual(data["component_type"], self.comp_attr["component_type"])
		#self.assertEqual(data["order"], self.comp_attr["order"])

	#def test_image_component(self):
