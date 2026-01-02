import { Briefcase, FileText, GraduationCap, HeartIcon, Home, MessageSquare } from "lucide-angular";
const metadata={
  companyName :'AccompagneFrance',
  phone : '+33 6 12 34 56 78',
  ownerName : 'Prénom Nom',
  whatsup : '+33 6 12 34 56 78',
  address : 'Siège social : 123 Avenue des Champs-Élysées, 75008 Paris.',
  seriNumber:'[Numéro SIRET]'
}
  
export const SITE_CONFIG = {
  appName: 'AccompagneFrance',
   owner: {
    fullName: metadata.ownerName,
    role: 'Accompagnateur administratif',
    contact: {
    email: 'contact@accompagnefrance.com',
    phone: metadata.phone,
    whatsapp:  metadata.whatsup
  },

  location: {
    city: 'Paris',
    country: 'France'
  },
  },
   team : [
  {
    name: "Thomas Dubois",
    role: "Expert en Droit des Étrangers",
    bio: "Ancien conseiller juridique avec 10 ans d'expérience dans l'accompagnement administratif.",
  },
  {
    name: "Sarah Martin",
    role: "Conseillère en Intégration",
    bio: "Spécialiste du logement et des démarches de vie quotidienne en France.",
  },
  {
    name: "Karim Benali",
    role: "Interprète & Accompagnateur",
    bio: "Polyglotte passionné par l'aide aux nouveaux arrivants et la facilitation culturelle.",
  },
],

  heroSectionData:{
     title: 'Votre parcours en France,',
     highlight: 'simplifié et accompagné',
     heroSubtitle: ' Nous accompagnons les personnes étrangères dans leurs démarches administratives en France avec professionnalisme et bienveillance.',
  },




  services:[
     {
    icon: FileText,
    title: "Accompagnement complet",
    description:
      "Prise en charge totale de vos démarches administratives, de la constitution du dossier à son dépôt en préfecture.",
  },
  {
    icon: MessageSquare,
    title: "Préparation à l'entretien",
    description:
      "Coaching personnalisé pour vous préparer aux entretiens en préfecture avec des simulations et conseils pratiques.",
  },
  {
    icon: GraduationCap,
    title: "Titres de séjour étudiants",
    description:
      "Assistance spécialisée pour les étudiants internationaux : inscription, titre de séjour, et renouvellement.",
  },
  {
    icon: Briefcase,
    title: "Autorisation de travail",
    description: "Constitution de dossiers pour autorisation de travail, changement de statut, et carte de résident.",
  },
  {
    icon: Home,
    title: "Regroupement familial",
    description: "Accompagnement dans les démarches de regroupement familial avec un suivi complet du dossier.",
  },
  {
    icon: HeartIcon,
    title: "Naturalisation",
    description:
      "Préparation complète de votre dossier de naturalisation et accompagnement jusqu'à l'obtention de la nationalité.",
  },
  ],
 

legal: {
  privacyPolicy: {
    title: 'Politique de Confidentialité',
    sections: [
      {
        heading: 'Protection des données',
        content: 'Conformément au RGPD, nous nous engageons à protéger vos données personnelles. Les informations collectées via notre formulaire de contact sont utilisées uniquement pour répondre à vos demandes.'
      },
      {
        heading: 'Vos droits',
        content: "Vous disposez d'un droit d'accès, de rectification et de suppression de vos données. Pour l'exercer, contactez-nous à l'adresse email : "
      }
    ]
  },
    cgv: {
      title: 'Conditions Générales de Vente (CGV)',
      sections: [
        {
          title: 'Objet',
          content:
            "Les présentes CGV régissent la vente des services d'accompagnement administratif proposés par "+metadata.companyName+" ."
        },
        {
          title: 'Tarifs',
          content:
            'Les prix de nos prestations sont indiqués en euros toutes taxes comprises (TTC).'
        },
        {
          title: 'Droit de rétractation',
          content:
            'Conformément à la loi, vous disposez d’un délai de 14 jours pour exercer votre droit de rétractation.'
        }
      ]
    },
     mentionsLegales: {
      title: 'Mentions Légales',
      sections: [
        {
          title: '1. Éditeur du site',
          paragraphs: [
            'Le site Accompagne France est édité par la société '+metadata.companyName+', au capital de [Montant] euros.',
            metadata.address,
            'Immatriculée au RCS de Paris sous le numéro ' + metadata.seriNumber+" ."
          ]
        },
        {
          title: '2. Hébergement',
          paragraphs: [
            'Le site est hébergé par Vercel Inc., situé au 340 S Lemon Ave #1142, Walnut, CA 91789, États-Unis.'
          ]
        },
        {
          title: '3. Propriété intellectuelle',
          paragraphs: [
            'L’ensemble du contenu de ce site (textes, images, logos) est la propriété exclusive de [Nom de l’Agence].'
          ]
        }
      ]
    }
  },


  footer: {
      footerText: 'Tous droits réservés.',

  },

};