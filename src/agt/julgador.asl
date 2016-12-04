// Agent julgador in project gerenciador_frota

/* Initial beliefs and rules */

/* Initial goals */


/* Plans */
	
+!kqml_received(Remetente, tell, Msg, MsgId) <- .send(reputa, achieve, atualiza(Msg,Remetente)).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have a agent that always complies with its organization  
{ include("$jacamoJar/templates/org-obedient.asl") }
