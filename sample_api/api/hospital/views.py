from django.shortcuts import render
from django.http import HttpResponse, JsonResponse
from django.views.decorators.csrf import csrf_exempt
from .models import *
import json

# Create your views here.
@csrf_exempt
def doctor(request):
    if request.method == 'GET':
        doctor = Doctor.objects.all()
        response = {}
        response["doctors"] = []
        for d in doctor:
            response["doctors"].append({"name":d.name, "lastname": d.lastname, "age": d.age});
        return JsonResponse(response)

    elif request.method == "POST":
        print("post doctor")
        try:
            data = json.loads(request.body.decode("utf-8"))
            doctor = Doctor.objects.create(name=data["name"], lastname=data["lastname"], age=data["age"])
            doctor.save()
            return JsonResponse({"status":"OK", "message":""})
        except Exception as e:
            print("wrong format!!!!!\n\n\n\n")
            return JsonResponse({"status":"FAIL", "message":"wrong data format"})


@csrf_exempt
def doctor_single(request, doctor_id):
    if request.method == "GET":
        doctor = Doctor.objects.filter(id=int(doctor_id))
        if doctor.exists():
            doctor = doctor.first()
        return JsonResponse({"name": doctor.name, "lastname": doctor.lastname,
                             "age": doctor.age})
    # Posts a single doctor with the fields name,lastname and age returns error message if the format is wrong
    elif request.method == "POST":
        print("post doctor")
        try:
            data = json.loads(request.body.decode("utf-8"))
            doctor = Doctor.objects.create(name=data["name"], lastname=data["lastname"], age=data["age"])
            doctor.save()
            return JsonResponse({"status":"OK", "message":""})
        except Exception as e:
            print("wrong format!!!!!\n\n\n\n")
            return JsonResponse({"status":"FAIL", "message":"wrong data format"})
    elif request.method == "PUT":
        data = json.loads(request.body.decode("utf-8"))
        doctor = Doctor.objects.filter(id=int(doctor_id))
        if doctor.exists():
            doctor = doctor.first()
        else:
            return JsonResponse({"status": "FAIL",
                                 "message": "doctor does not exist"})
        try:
            if "age" in data:
                doctor.age = data["age"]
            if "name" in data:
                doctor.name = data["name"]
            if "lastname" in data:
                doctor.lastname = data["lastname"]
            doctor.save()
        except Exception:
            return JsonResponse({"status": "FAIL", "message": "missing field"})
        return JsonResponse({"status": "OK", "message": ""})

    elif request.method == "DELETE":
        doctor = Doctor.objects.filter(id=int(doctor_id))
        if doctor.exists():
            doctor.delete()
            return JsonResponse({"status":"OK", "message":""})
        else:
            return JsonResponse({"status":"FAIL", "message":"doctor does not exist"})

@csrf_exempt
def patient_single(request, patient_id):
    # Given an id, returns a json response contatining the name, lastname and age of the patient having the given id.
    if request.method == "GET":
        print(request)
        patient = Patient.objects.filter(id=int(patient_id))
        if patient.exists():
            patient = patient.first()
        else:
            return JsonResponse({"status":"FAIL", "message":"patient does not exist"})
        return JsonResponse({"name":patient.name, "lastname":patient.lastname, "age":patient.age})

    # Given an id and at most 3 parameters of type name, lastname and age; replaces the name and/or the lastname and/or the age
    # of the patient having the given id with the given name, i.e if someone wants to change only age of a patient, they give only age as parameter, and only age
    # of the patient changes, the name and lastname remains the same.
    elif request.method == "PUT":
        data = json.loads(request.body.decode("utf-8"))
        print(patient_id)
        print(data)
        patient = Patient.objects.filter(id=int(patient_id))
        if patient.exists():
            patient = patient.first()
        else:
            return JsonResponse({"status":"FAIL", "message":"patient does not exist"})
        try:
            if "age" in data:
                patient.age = data["age"]
            if "name" in data:
                patient.name = data["name"]
            if "lastname" in data:
                patient.lastname = data["lastname"]
            patient.save()
        except:
            return JsonResponse({"status":"FAIL", "message":"missing field"})
        return JsonResponse({"status":"OK", "message":""})
    elif request.method=="DELETE":
        patient = Patient.objects.filter(id=int(patient_id))
        if patient.exists():
            patient.delete()
            return JsonResponse({"status":"OK", "message":""})
        else:
            return JsonResponse({"status":"FAIL", "message":"patient does not exist"})

@csrf_exempt
def department(request):
    print(request)
    if request.method == "GET":
        department=Department.objects.all()
        dep = {}
        dep_records=[]
        for e in department:
            nam=e.name
            record={"name":nam}
            dep_records.append(record)

        dep["departments"]=dep_records
        return JsonResponse(dep)

@csrf_exempt
def department_single(request, department_id):
    # Given an id, returns a json response contatining the name of the department having the given id.
    if request.method == "GET":
        print(request)
        department = Department.objects.filter(id = int(department_id))
        if department.exists():
            department = department.first()
        return JsonResponse({"name": department.name})

    # Given an id and a parameter of type name, replaces the name of the department having the given id with the given name.
    elif request.method == "PUT":
        data = json.loads(request.body.decode("utf-8"))
        department = Department.objects.filter(id = int(department_id))
        if department.exists():
            department = department.first()
        else:
            return JsonResponse({"status":"FAIL", "message":"department does not exist"})
        try:
            if "name" in data:
                department.name = data["name"]
            department.save()
        except:
            return JsonResponse({"status": "FAIL", "message": "missing field"})
        return JsonResponse({"status":"OK", "message":""})

    elif request.method == "DELETE":
        department = Department.objects.filter(id = int(department_id))
        if department.exists():
            department.delete()
            return JsonResponse({"status":"OK", "message":""})
        else:
            return JsonResponse({"status":"FAIL", "message":"department does not exist"})

@csrf_exempt
def patient(request):
    # Returns all patients as a dictionary with the patient's name,lastname and age
    if request.method == "GET":
        patients = Patient.objects.all()
        response = {}
        response["patients"] = []
        for p in patients:
            response["patients"].append({"name":p.name, "lastname": p.lastname, "age": p.age});
        return JsonResponse(response)
    elif request.method == "POST":
        print("post patient")
        try:
            data = json.loads(request.body.decode("utf-8"))
            patient = Patient.objects.create(name=data["name"], lastname=data["lastname"], age=data["age"])
            patient.save()
            return JsonResponse({"status":"OK", "message":""})
        except Exception as e:
            print("wrong format!!!!!\n\n\n\n")
            return JsonResponse({"status":"FAIL", "message":"wrong data format"})
@csrf_exempt
def rendezvous(request):
    if request.method == "GET":
        rendezvous=Rendezvous.objects.all()
        dep = {}
        dep_records=[]
        for e in rendezvous:
            nam=e.doctor.name+" "+e.doctor.lastname
            nam2=e.patient.name+" "+e.patient.lastname
            tim=e.date
            record={"Doctor name and lastname":nam,"Patient name and lastname":nam2,"time":tim}
            dep_records.append(record)


        dep["rendezvous"]=dep_records
        return JsonResponse(dep)
    elif request.method == "POST":
        try:
            data = json.loads(request.body.decode("utf-8"))
            print(data["doctor_id"]+ " "+data["patient_id"])
            patient = Patient.objects.filter(id=int(data["patient_id"]))
            if patient.exists():
                patient = patient.first()
            doctor = Doctor.objects.filter(id=int(data["doctor_id"]))
            if doctor.exists():
                doctor = doctor.first()

            rendezvous=Rendezvous.objects.create(doctor=doctor,patient=patient)
            rendezvous.save()
            return JsonResponse({"status":"OK", "message":"rendezvous added"})
        except Exception as e:
            print("wrong format!!!!!\n\n\n\n")
            return JsonResponse({"status":"FAIL", "message":"wrong data format"})
    elif request.method == "GET":

        # Returns all rendezvouses in json format

        rendezvous_list=Rendezvous.objects.all()
        ren = {}
        ren_records=[]
        for r in rendezvous_list:
            date=r.date
            active = "No"
            if r.is_active:
                active = "Yes"
            record={"Doctor Name":r.doctor.name + " " + r.doctor.lastname,"Patient":r.patient.name+" "+r.patient.lastname, "Date":date, "Is Active":active}
            ren_records.append(record)

        ren["rendezvous"]=ren_records
        return JsonResponse(ren)

@csrf_exempt
def rendezvous_single(request,rendezvous_id):
    if request.method == "GET":
        print(request)
        rendezvous=Rendezvous.objects.filter(id = int(rendezvous_id))
        if rendezvous.exists():
            rendezvous=rendezvous.first()
            return JsonResponse({"Doctor Name":rendezvous.doctor.name + " " + rendezvous.doctor.lastname,"Patient":rendezvous.patient.name+" "+rendezvous.patient.lastname,"Time":str(rendezvous.date)})
        else:
            return JsonResponse({"status":"FAIL", "message":"rendezvous does not exist"})
    elif request.method == "DELETE":
        # deletes a rendezvous by its id
        rendezvous = Rendezvous.objects.filter(id = int(rendezvous_id))
        if rendezvous.exists():
            rendezvous.delete()
            return JsonResponse({"status":"OK", "message":""})
        else:
            return JsonResponse({"status":"FAIL", "message":"rendezvous does not exist"})
