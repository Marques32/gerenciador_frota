// Agent condutor in project gerenciador_frota

/* Initial beliefs and rules */


/* Initial goals */

/* Plans */

+!observe : true <- .my_name(Me);
					.concat("Artecfat_", Me, ArtName);
					makeArtifact(ArtName, "gerenciador_frota_staghunt.ComputadorDeBordo",[],C);
					focus(C).
					
+!envia_ct : true <- envia_ct[artifact_id(Id)].

+!create_and_use : true <- inc[artifact_id(Id)].

+cooperacao(Ct) : true <- .my_name(Me);
						.send(juiz, achieve, coloca_ct(Ct,Me)).

+velocidade(X) : true <- .my_name(Me);
						.send(juiz, achieve, atualiza(X,Me)).

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

{ include("$jacamoJar/templates/org-obedient.asl") }
