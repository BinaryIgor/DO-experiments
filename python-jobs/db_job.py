import time
from os import environ

print("About to run some long-running automation task...")
time.sleep(5)
print()
for k, v in environ.items():
    print(f'{k} - {v}')
print()
time.sleep(5)
print("Automation - done.")