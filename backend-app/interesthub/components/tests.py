from django.test import TestCase
from .models import *
from .serializers import *

class ComponentTests(TestCase):

	def setUp(self):
		self.comp_attr = {
			"component_type": "text",
			"order": 2
		}

		self.dummy_type = {
			"component_type": "dummy",
			"order": 1
		}

		#img = ImageComponent.objects.create(data="http://www.techspire.net/wp-content/uploads/2016/11/python-django-logo.jpg")
		self.t_comp = Component.objects.create(**self.comp_attr)
		self.serializer = ComponentSerializer(instance=self.t_comp, data=self.comp_attr)

	def test_text_component(self):
		self.assertTrue(self.serializer.is_valid())		
		data = self.serializer.validated_data
		
		self.assertEqual(set(data.keys()), set(["component_type", "order"]))
		self.assertEqual(data["component_type"], self.comp_attr["component_type"])
		self.assertEqual(data["order"], self.comp_attr["order"])

	def test_invalid_component_type(self):
		self.comp = Component.objects.create(**self.dummy_type)
		self.serializer = ComponentSerializer(instance=self.comp, data=self.dummy_type)
		self.assertFalse(self.serializer.is_valid())
		# If an error is expected, the problematic keys are tested:
		self.assertEqual(set(self.serializer.errors), set(["component_type"]))

	def test_create_text_component(self):
		self.assertTrue(self.serializer.is_valid())		
		data = self.serializer.validated_data
		temp = self.serializer.create(data)
		self.assertEqual(temp.component_type, self.comp_attr["component_type"])
		self.assertEqual(temp.order, self.comp_attr["order"])

	def test_create_and_update_text_component(self):
		self.assertTrue(self.serializer.is_valid())		
		data = self.serializer.validated_data
		temp = self.serializer.create(data)
		self.assertEqual(temp.component_type, self.comp_attr["component_type"])
		self.assertEqual(temp.order, self.comp_attr["order"])

		self.serializer = ComponentSerializer(instance=temp, data={"component_type": "image", "order": 2})
		self.assertTrue(self.serializer.is_valid())		
		data = self.serializer.validated_data
		temp = self.serializer.update(temp, data)
		self.assertEqual(temp.component_type, "image")
		self.assertEqual(temp.order, 2)

	# Couldn't write a test for a Component containing an ImageComponent
	#def test_create_component(self):
		#img = ImageComponent.objects.create(data="http://www.techspire.net/wp-content/uploads/2016/11/python-django-logo.jpg")
		#img.save()
		#self.comp = Component.objects.create(image=img, component_type="image")
		#self.serializer = ComponentSerializer2(instance=self.comp)
		#self.assertTrue(self.serializer.is_valid())		
		#data = self.serializer.validated_data

		#self.assertEqual(set(data.keys()), set(["image", "component_type"]))
		#self.assertEqual(data["image"], self.img_comp["image"])
		#self.assertEqual(data["component_type"], self.img_comp["component_type"])
