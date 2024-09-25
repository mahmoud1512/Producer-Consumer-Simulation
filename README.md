# Producer-Consumer-Simulation
A simulation of the production and consuming using queues and machines

---

# OverView
- this project aims to simulate the process of creating products in a multi-stages environment
- the main challenge of it is syncronization since every machine is running on a different thread
- Another challenge is real time updates between frontend and backend
- the simulation can be replayed after it ends
---
# Design Decisions
- apply the Observer pattern to make machines monitor queues to know exactly when to take products and when not
- apply the snapShot pattern to make momento objects keeping data of system updates so when replaying we can read these Momento objects
- Open webSocket Connetion between frontend and backend for real time updates
- Use locks to handle syncronization problems
---
# Technology Stack:-

- Frontend:
   - Vue js
   - Stomp js (for webSocket messaging)
- Backend
   - Spring boot
   - Web Socket
 
---
# Design patterns:-

- Observer Pattern
- SnapShot pattern
- Producer Consumer pattern
  
