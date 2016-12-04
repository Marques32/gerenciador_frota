// Agent condutor in project gerenciador_frota

/* Initial beliefs and rules */


/* Initial goals */

/* Plans */

+!observe : true <- .my_name(Me);
					.concat("Artecfat_", Me, ArtName);
					makeArtifact(ArtName, "gerenciador_frota_staghunt.ComputadorDeBordo",[],C);
					focus(C).
					
+!create_and_use : true <- inc[artifact_id(Id)].
					
+velocidade(X) : true <- .my_name(Me);
						.send(reputa, achieve, atualiza(X,Me)).

+listar : true <- .send(reputa, achieve, listar).

//+velocidade(X) : X>100 <- .print(X).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

{ include("$jacamoJar/templates/org-obedient.asl") }
