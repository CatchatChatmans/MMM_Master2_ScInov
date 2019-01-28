var fs = require('fs');
var jsonContent = JSON.parse(fs.readFileSync('scinov-mda-export.json', 'utf8'));

var events = [];

jsonContent.events.forEach(event => {
    let filteredEvent = {"identifiant" : event.fields["identifiant"],
                         "animateurs": event.fields["animateurs"],
                         "departement": event.fields["departement"],
                         "adresse": event.fields["adresse"],
                         "geolocalisation": event.fields["geolocalisation"],
                         "inscription_necessaire": event.fields["inscription_necessaire"],
                         "image_source": event.fields["image_source"],
                         "type_d_animation": event.fields["type_d_animation"],
                         "titre_fr": event.fields["titre_fr"],
                         "mots_cles_fr": event.fields["mots_cles_fr"],
                         "horaires_detailles_fr": event.fields["horaires_detailles_fr"],
                         "description_longue_fr": event.fields["description_longue_fr"],
                         "description_fr": event.fields["description_fr"],
                         "dates": event.fields["dates"],
                         "code_postal": event.fields["code_postal"],
                         "ville": event.fields["ville"],
                         "lien_canonique": event.fields["lien_canonique"],
                         "thematiques": event.fields["thematiques"],
                         "lien_d_inscription": event.fields["lien_d_inscription"]
                        };


    events.push(filteredEvent);
   
});
let jsonObjet = {"events": events,
"parcours" : 1,
"users" : 1
  };

console.log(JSON.stringify(jsonObjet));

fs.writeFile('jsonData.json', JSON.stringify(jsonObjet));


