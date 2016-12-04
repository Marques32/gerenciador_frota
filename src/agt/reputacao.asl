// Agent reputacao in project gerenciador_frota

/* Initial beliefs and rules */

/* Initial goals */
!criar_armazenador_reputacao.

/* Plans */
+!criar_armazenador_reputacao <- makeArtifact("arm_reputacao", "gerenciador_frota_staghunt.ArmazenadorReputacao",[],A);
								focus(A).

+!atualiza(Valor, Quem) : true <- atualiza(Valor,Quem).

+!listar : true <-listar.

{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have a agent that always complies with its organization  
{ include("$jacamoJar/templates/org-obedient.asl") }
