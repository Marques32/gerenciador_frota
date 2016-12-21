// Agent user in project exemplo_01

/* Initial beliefs and rules */

/* Initial goals */
!fazer_conducao.
/* Plans */
+!fazer_conducao <- .concat("sch_","u1",SchName);
					makeArtifact(SchName, "ora4mas.nopl.SchemeBoard",["src/org/conducao.xml", fazerConducao, false, true], SchArtId);
					.my_name(Me); setOwner(Me)[artifact_id(SchArtId)];
					focus(SchArtId);
					addSchemeWhenFormationOk(SchName).
					


{ include("$jacamoJar/templates/common-cartago.asl") }
{ include("$jacamoJar/templates/common-moise.asl") }

// uncomment the include below to have a agent that always complies with its organization  
{ include("$jacamoJar/templates/org-obedient.asl") }
