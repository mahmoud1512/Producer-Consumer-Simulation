<template>

<div id="simulator">
   
            <div id="toolbar">
            <button @click="start_simulation()" class="tool" id="start">Start Simulation <i class="fa-regular fa-circle-play"></i> </button>
            <button @click="replay()" class="tool" id="replay">Replay <i class="fa-solid fa-reply"></i></button>
            <button @click="drawqueue()" class="tool" id="replay"> add queue</button>
            <button @click="drawmachine()" class="tool" id="replay">add machine</button>
            <button @click="drawlink()" class="tool" id="replay">add link</button>  
            <button @click="clear()" class="tool">Clear</button>
            </div>

    
<div id="platform">

 <v-stage :config="configKonva" ref="stage" @click="drawpart">
    <v-layer>

     <v-arrow
  v-for="(line, index) in lines"
  :key="index"
  :config="{
    x: 0,
    y: 0,
    points: line.points,   
    stroke: 'black',
    strokeWidth: 4,
    draggable: false,
    lineCap: 'round',
    lineJoin: 'round',
    pointerLength: 10,     // Length of the arrowhead
    pointerWidth: 10,      // Width of the arrowhead
    fill: 'black',         // Fill color for the arrowhead
  }"
/>

      <v-rect
        v-for="(queue, index) in queues"
        :key="index"
        :config="{
          x: queue.x,
          y: queue.y,
          width: 70,
          height: 60,
          fill: queue.fill, 
          stroke: 'black', 
          strokeWidth: 2,
          draggable: false,
          id: queue.id,
        }"
        @click="shapeClicked('queue', index)" ></v-rect>

      <v-circle
        v-for="(machine, index) in machines"
        :key="index"
        :config="{
          x: machine.x,
          y: machine.y,
          radius: 40,
          fill: machine.fill,
          stroke: 'black',
          strokeWidth: 2,
          draggable: false,
          id: machine.id,
        }"
     @click="shapeClicked('machine', index)">
      </v-circle>
      
       

     

       <v-text
         v-for="(txt,index) in texts"
         :key="index"
         :config="{
            x:txt.x,
            y:txt.y,
            id:txt.id,
            text:txt.text,
         }"
      >

      </v-text>
  



    </v-layer>
  </v-stage>
</div> 

  
</div>
</template>
<script>
import { ref } from "vue"
import { Stomp } from '@stomp/stompjs';
import SockJS from 'sockjs-client';
import { forEach } from 'sockjs-client/lib/transport-list';

export default  {
  name:'App',
  data() {
    
    return {
      value: null,
      intervalId: null,
      configKonva: {
          width: window.innerWidth,
          height: 800,
      },
      stompClient:null,
      isdraw:false,
      lin:false,
      circ:false,
      rectangle:false,
      currentShape:null,
      currenttext:null,
      stop:false,
      products:0,
      confirm:false,
      graph:[],
      begin:false,
      machineid:0,
      queueid:-1,
      queues:[],
      machines:[],
      lines:[],
      shapes:[],
      texts:[],
      fetched: [],
    };
  },
  mounted()
  {
    
     this.connectToWebSocket();
  },
  methods: {
    drawpart()
    {
       if(!this.lin&&this.isdraw)
       {
          console.log(this.circ);
             if(this.circ)
         { 
                this.machineid++;
                const stage = this.$refs.stage.getStage();
                 console.log(stage);
                if (stage) {
                  const position = stage.getPointerPosition();
                 
                  if (position) {
                    this.currentShape = {
                      x: position.x,
                      y: position.y,
                      fill:"rgb(128,128,128)",
                      strokeWidth:2,
                      radius: 40,
                      id:"m"+String(this.machineid)
                    };
                    this.currenttext={
                       x:position.x-10,
                       y:position.y-10,
                       id:this.currentShape.id,
                       text: "m" + String(this.machineid)  // Corrected here
                    };
                  }
                }
         }
         else if(this.rectangle)
         {
                this.queueid++;
                const stage = this.$refs.stage.getStage();
                if (stage) {
                  const position = stage.getPointerPosition();
                  if (position) {
                    this.currentShape = {
                      x: position.x,
                      y: position.y,
                      fill:"#FFFF00",
                      strokeWidth:2,
                      width: 70,
                      height: 60,
                      id: "q" + String(this.queueid) // Corrected here
                    };
                    this.currenttext={
                       x:position.x+30,
                       y:position.y+20,
                       id:this.currentShape.id,
                       text: "q" + String(this.queueid) // Corrected here
                    };
                  }
                }
         }
         this.stopDrawing();
       }

       
    },
    
    stopDrawing() {
      
     
          if (this.isdraw) {
        this.isdraw = false;
        console.log("xxxxx");
        if(this.circ)
        {
          console.log("done");
          this.shapeType = 'Circle';
          console.log(this.currentShape);
          this.machines.push({ ...this.currentShape });
          this.texts.push({...this.currenttext})
          this.shapes.push({...this.currentShape});
                 
        }
      
      else if(this.rectangle){
        this.shapeType = 'Rectangle';

          this.queues.push({...this.currentShape});
           this.texts.push({...this.currenttext})
           console.log(this.currenttext);
            this.shapes.push({...this.currentShape});
      }
      else if(this.lin)
      {
        this.shapeType = 'Line';
        this.lines.push({...this.currentShape});
        this.shapes.push({...this.currentShape});
      }
        this.currentShape = null;
        this.currenttext=null;
        this.rectangle=false;
        this.circ=false;
      }
    },
  drawqueue()
{
  this.isdraw=true;
  this.rectangle=true;
  this.circ=false;
  this.lin=false;
},

drawmachine()
{
   this.isdraw=true;
  this.circ=true;
  this.rect=false;
  this.lin=false;
},
drawlink()
{
    this.isdraw=true;
  this.circ=false;
  this.rect=false;
  this.lin=true;
  this.edge={
    source:null,
    destination:null
  };
  this.currentShape={
    points:[]
  };
},

shapeClicked(type, index) {
    if (this.isdraw&&this.lin) {
        if (type === "queue") {
          console.log(this.edge.source !== null);
            if (this.edge.source !== null) {
                this.edge.destination = this.queues[index].id;
                this.currentShape.points[2] = this.queues[index].x+70;
                this.currentShape.points[3] = this.queues[index].y+30;
                this.isdraw = false;
                if(this.edge.source!==this.edge.destination)
                {
                       this.graph.push({ ...this.edge });      
                }     
                this.lines.push({ ...this.currentShape });
                this.edge = { source: null, destination: null }; // Reset edge
                this.currentShape = null;
            } else {
                this.edge.source = this.queues[index].id;
                this.currentShape.points[0] = this.queues[index].x+35;
                this.currentShape.points[1] = this.queues[index].y+30;
                console.log(this.edge);
            }
        } else if (type === "machine") {
            if (this.edge.source !== null) {
                this.edge.destination = this.machines[index].id;
                this.currentShape.points[2] = this.machines[index].x+this.machines[index].radius;
                this.currentShape.points[3] = this.machines[index].y;
                this.isdraw = false;
                if(this.edge.source!==this.edge.destination)
                {
                       this.graph.push({ ...this.edge });      
                }     
                this.lines.push({ ...this.currentShape });
                this.edge = { source: null, destination: null }; // Reset edge
                this.currentShape = null;
            } else {
                this.edge.source = this.machines[index].id;
                this.currentShape.points[0] = this.machines[index].x;
                this.currentShape.points[1] = this.machines[index].y;
            }
        }
        console.log(this.graph);
    }
},


clear()
{
   this.queues=[];
   this.shapes=[];
   this.machines=[];
   this.lines=[];
   this.texts=[];
   this.queueid=-1;
   this.machineid=0;
   this.sendClearMessageToBackend("Clear");
   
},



connectToWebSocket() {
      // Create the WebSocket connection
      const socket = new SockJS('http://localhost:8083/ws'); 
      this.stompClient = Stomp.over(socket);

      this.stompClient.connect({}, (frame) => {
        console.log('Connected: ' + frame);

        this.stompClient.subscribe('/topic/data', (message) => {
          const receivedMessage = message.body;
          this.handleMessage(receivedMessage);
        });

        this.stompClient.subscribe('/topic/replay', (message) => {
          const replayData = JSON.parse(message.body); 
          this.handleReplay(replayData);  
        });
      });
    },

    
    requestReplay() {
      this.stompClient.send("/app/replay", {}, {});  
    },
   
    sendSystemMessageToBackend(message) {
      this.stompClient.send("/app/receive", {}, JSON.stringify(message));  
    },
    
    sendClearMessageToBackend(message) {
      this.stompClient.send("/app/clear", {}, JSON.stringify(message));  
    },

    handleMessage(message) {
      console.log(message);
       const words=message.split(" ");
       if(words[0][0]==='m')
       {
            const machine=this.machines.find((r)=>r.id===words[0]);
            machine.fill=words[1];  
           
       }
       else
       { 
           const text=this.texts.find((r)=>r.id===words[0])
           text.text=words[1];
       }

    },
   async handleReplay(replayData) {
    
    for(let i=0;i<this.texts.length;i++)
    {
        this.texts[i].text=this.texts[i].id;
    }
    await new Promise(resolve => setTimeout(resolve, 1000));
    
    for (let i = 0; i < replayData.length; i++) {
        let order = replayData[i].snapShot; 
        this.handleMessage(String(order));
       await new Promise(resolve => setTimeout(resolve, 250));
    }
},

  
    start_simulation()
    {
         console.log(this.graph);
         this.sendSystemMessageToBackend(this.graph); 
    },
    replay()
    {
        this.requestReplay();
    }

     


  },
 
};
</script>










<style>
@import "~@fortawesome/fontawesome-free/css/all.min.css";
@keyframes rotateAnimation {
    from {
        transform: rotate(0deg);
    }
    to {
        transform: rotate(360deg);
    }
}

@-webkit-keyframes pulse {
    0% { -webkit-transform: scale(1); }
    50% { -webkit-transform: scale(1.1); }
    100% { -webkit-transform: scale(1); }
}
@keyframes pulse {
    0% { transform: scale(1); }
    50% { transform: scale(1.1); }
    100% { transform: scale(1); }
}

#simulator
{
  height: 900px;
  background-image: url(https://images.freecreatives.com/wp-content/uploads/2016/04/Website-Backgrounds-For-Desktop.jpg);
  background-size: cover;
  background-repeat: no-repeat;
  background-position: center center;
}


#toolbar {
  width: 70%;
  margin-left: 15%;
  margin-right: 15%;
  display: flex;
   border: 2px solid black;
  justify-content: space-around; 
  border-radius: 15px; 
  overflow: hidden; 
  background: white;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
  background-color: floralwhite;
}

.tool {
  width:20%;
  border: solid;
  padding: 10px;
  background-color: #3498db;
  color: white;
  font-size: 16px;
  cursor: pointer;
  border-radius: 15px; 
  transition: background-color 0.3s;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}
#confirm:hover
{
   opacity: 70%;
   animation: pulse 1.5s ;
   color:red
}
#start:hover
{
   opacity: 70%;
   animation: pulse 1.5s ;
   color: red;
}
#replay:hover
{
    opacity: 70%;
   animation: pulse 1.5s ;
   color:red
}
#platform
{
  margin-top: 20px;
  border: 2px solid black;
  background-color: white;
   width: 100%;
  
  margin-top: 3%;
  height:600px;
  box-shadow: 0px 4px 6px rgba(0, 0, 0, 0.1);
}
.tool:hover {
  background-color: #2980b9;
}

</style>