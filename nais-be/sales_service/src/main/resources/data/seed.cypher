// ================= ACCOUNTS =================
MERGE (a1:Account {id: randomUUID(), name: 'TechNova', email: 'contact@technova.com', phone: '111-111', concrete: true})
MERGE (a2:Account {id: randomUUID(), name: 'GreenField', email: 'contact@greenfield.com', phone: '222-222', concrete: true})
MERGE (a3:Account {id: randomUUID(), name: 'Skyline Logistics', email: 'contact@skyline.com', phone: '333-333', concrete: true})
MERGE (a4:Account {id: randomUUID(), name: 'Medionix', email: 'contact@medionix.com', phone: '444-444', concrete: true})
MERGE (a5:Account {id: randomUUID(), name: 'BlueEdge Retail', email: 'contact@blueedge.com', phone: '555-555', concrete: true})

// ================= CONTACTS =================
MERGE (c1:Contact {id: randomUUID(), firstName: 'Ana', lastName: 'Petrović', email: 'ana.p@technova.com', phone: '101-101', concrete: true})
MERGE (c2:Contact {id: randomUUID(), firstName: 'Marko', lastName: 'Jovanović', email: 'marko.j@greenfield.com', phone: '102-102', concrete: true})
MERGE (c3:Contact {id: randomUUID(), firstName: 'Jelena', lastName: 'Nikolić', email: 'jelena.n@medionix.com', phone: '103-103', concrete: true})
MERGE (c4:Contact {id: randomUUID(), firstName: 'Stefan', lastName: 'Ilić', email: 'stefan.i@skyline.com', phone: '104-104', concrete: true})
MERGE (c5:Contact {id: randomUUID(), firstName: 'Milica', lastName: 'Popović', email: 'milica.p@blueedge.com', phone: '105-105', concrete: true})
MERGE (c6:Contact {id: randomUUID(), firstName: 'Nikola', lastName: 'Vuković', email: 'nikola.v@technova.com', phone: '106-106', concrete: true})
MERGE (c7:Contact {id: randomUUID(), firstName: 'Ivana', lastName: 'Lukić', email: 'ivana.l@greenfield.com', phone: '107-107', concrete: true})
MERGE (c8:Contact {id: randomUUID(), firstName: 'Lazar', lastName: 'Simić', email: 'lazar.s@medionix.com', phone: '108-108', concrete: true})
MERGE (c9:Contact {id: randomUUID(), firstName: 'Mina', lastName: 'Ristić', email: 'mina.r@blueedge.com', phone: '109-109', concrete: true})
MERGE (c10:Contact {id: randomUUID(), firstName: 'Uroš', lastName: 'Milenković', email: 'uros.m@skyline.com', phone: '110-110', concrete: true})

// ================= RELATIONSHIPS Contact → Account =================
MERGE (c1)-[:WORKS_FOR]->(a1)
MERGE (c2)-[:WORKS_FOR]->(a2)
MERGE (c3)-[:WORKS_FOR]->(a4)
MERGE (c4)-[:WORKS_FOR]->(a3)
MERGE (c5)-[:WORKS_FOR]->(a5)
MERGE (c6)-[:WORKS_FOR]->(a1)
MERGE (c7)-[:WORKS_FOR]->(a2)
MERGE (c8)-[:WORKS_FOR]->(a4)
MERGE (c9)-[:WORKS_FOR]->(a5)
MERGE (c10)-[:WORKS_FOR]->(a3)

// ================= LEAD LIFECYCLES =================
MERGE (lc1:LeadLifecycle {id: randomUUID(), name: 'Standard Sales Flow', description: 'Standard sales process'})
MERGE (lc2:LeadLifecycle {id: randomUUID(), name: 'Partner Referral Flow', description: 'Flow for partner referrals'})

// ================= LEAD STATUSES =================
MERGE (s1:LeadStatus {id: randomUUID(), name: 'New', description: 'New lead'})
MERGE (s2:LeadStatus {id: randomUUID(), name: 'Contacted', description: 'Lead has been contacted'})
MERGE (s3:LeadStatus {id: randomUUID(), name: 'Qualified', description: 'Lead qualified'})
MERGE (s4:LeadStatus {id: randomUUID(), name: 'Pending Partner Review', description: 'Waiting partner review'})
MERGE (s5:LeadStatus {id: randomUUID(), name: 'Approved by Partner', description: 'Approved by partner'})
MERGE (s6:LeadStatus {id: randomUUID(), name: 'Closed', description: 'Closed lead'})

// ================= RELATIONSHIPS LeadStatus → LeadLifecycle =================
MERGE (lc1)-[:OWNS]->(s1)
MERGE (lc1)-[:OWNS]->(s2)
MERGE (lc1)-[:OWNS]->(s3)
MERGE (lc2)-[:OWNS]->(s4)
MERGE (lc2)-[:OWNS]->(s5)
MERGE (lc2)-[:OWNS]->(s6)

// ================= NEXT STATUS RELATIONSHIPS =================
MERGE (s1)-[:IS_NEXT]->(s2)
MERGE (s2)-[:IS_NEXT]->(s3)
MERGE (s4)-[:IS_NEXT]->(s5)
MERGE (s5)-[:IS_NEXT]->(s6)

// ================= LEADS =================
MERGE (l1:Lead {id: randomUUID(), description: 'Website Inquiry', createdAt: localdatetime('2025-10-05T14:23:00')})
MERGE (l2:Lead {id: randomUUID(), description: 'Cold Email Response', createdAt: localdatetime('2025-09-28T09:15:00')})
MERGE (l3:Lead {id: randomUUID(), description: 'Conference Contact', createdAt: localdatetime('2025-09-12T16:40:00')})
MERGE (l4:Lead {id: randomUUID(), description: 'Referral', createdAt: localdatetime('2025-08-30T11:05:00')})
MERGE (l5:Lead {id: randomUUID(), description: 'Inbound Call', createdAt: localdatetime('2025-08-25T08:50:00')})
MERGE (l6:Lead {id: randomUUID(), description: 'Social Media', createdAt: localdatetime('2025-07-29T18:12:00')})
MERGE (l7:Lead {id: randomUUID(), description: 'Demo Request', createdAt: localdatetime('2025-10-01T13:37:00')})
MERGE (l8:Lead {id: randomUUID(), description: 'Trial Signup', createdAt: localdatetime('2025-09-18T10:05:00')})
MERGE (l9:Lead {id: randomUUID(), description: 'Newsletter Click', createdAt: localdatetime('2025-08-12T09:30:00')})
MERGE (l10:Lead {id: randomUUID(), description: 'Referral Partner', createdAt: localdatetime('2025-07-25T15:45:00')})
MERGE (l11:Lead {id: randomUUID(), description: 'Promo Event', createdAt: localdatetime('2025-09-07T17:20:00')})
MERGE (l12:Lead {id: randomUUID(), description: 'Organic Search', createdAt: localdatetime('2025-08-18T12:10:00')})
MERGE (l13:Lead {id: randomUUID(), description: 'Support Inquiry', createdAt: localdatetime('2025-10-12T08:55:00')})
MERGE (l14:Lead {id: randomUUID(), description: 'Upsell Opportunity', createdAt: localdatetime('2025-09-02T14:50:00')})
MERGE (l15:Lead {id: randomUUID(), description: 'External Referral', createdAt: localdatetime('2025-07-28T16:33:00')})
MERGE (l16:Lead {id: randomUUID(), description: 'Cold Call', createdAt: localdatetime('2025-08-05T10:22:00')})
MERGE (l17:Lead {id: randomUUID(), description: 'Affiliate Link', createdAt: localdatetime('2025-09-22T11:18:00')})
MERGE (l18:Lead {id: randomUUID(), description: 'Returning Customer', createdAt: localdatetime('2025-10-08T09:47:00')})
MERGE (l19:Lead {id: randomUUID(), description: 'Download Whitepaper', createdAt: localdatetime('2025-07-31T13:05:00')})
MERGE (l20:Lead {id: randomUUID(), description: 'Beta Tester', createdAt: localdatetime('2025-08-15T12:55:00')})

// ================= RELACIJE LEADS =================
MERGE (l1)-[:HAS_CONTACT]->(c1)
MERGE (l1)-[:HAS_ACCOUNT]->(a1)
MERGE (l1)-[:HAS_LIFECYCLE]->(lc1)
MERGE (l1)-[:IS_STATUS]->(s1)

MERGE (l2)-[:HAS_CONTACT]->(c6)
MERGE (l2)-[:HAS_ACCOUNT]->(a1)
MERGE (l2)-[:HAS_LIFECYCLE]->(lc2)
MERGE (l2)-[:IS_STATUS]->(s2)

MERGE (l3)-[:HAS_CONTACT]->(c2)
MERGE (l3)-[:HAS_ACCOUNT]->(a2)
MERGE (l3)-[:HAS_LIFECYCLE]->(lc1)
MERGE (l3)-[:IS_STATUS]->(s3)

MERGE (l4)-[:HAS_CONTACT]->(c4)
MERGE (l4)-[:HAS_ACCOUNT]->(a3)
MERGE (l4)-[:HAS_LIFECYCLE]->(lc2)
MERGE (l4)-[:IS_STATUS]->(s4)

MERGE (l5)-[:HAS_CONTACT]->(c3)
MERGE (l5)-[:HAS_ACCOUNT]->(a4)
MERGE (l5)-[:HAS_LIFECYCLE]->(lc1)
MERGE (l5)-[:IS_STATUS]->(s5)

MERGE (l6)-[:HAS_CONTACT]->(c5)
MERGE (l6)-[:HAS_ACCOUNT]->(a5)
MERGE (l6)-[:HAS_LIFECYCLE]->(lc2)
MERGE (l6)-[:IS_STATUS]->(s6)

MERGE (l7)-[:HAS_CONTACT]->(c7)
MERGE (l7)-[:HAS_ACCOUNT]->(a2)
MERGE (l7)-[:HAS_LIFECYCLE]->(lc1)
MERGE (l7)-[:IS_STATUS]->(s2)

MERGE (l8)-[:HAS_CONTACT]->(c8)
MERGE (l8)-[:HAS_ACCOUNT]->(a3)
MERGE (l8)-[:HAS_LIFECYCLE]->(lc2)
MERGE (l8)-[:IS_STATUS]->(s3)

MERGE (l9)-[:HAS_CONTACT]->(c9)
MERGE (l9)-[:HAS_ACCOUNT]->(a4)
MERGE (l9)-[:HAS_LIFECYCLE]->(lc1)
MERGE (l9)-[:IS_STATUS]->(s1)

MERGE (l10)-[:HAS_CONTACT]->(c10)
MERGE (l10)-[:HAS_ACCOUNT]->(a5)
MERGE (l10)-[:HAS_LIFECYCLE]->(lc2)
MERGE (l10)-[:IS_STATUS]->(s5)

MERGE (l11)-[:HAS_CONTACT]->(c1)
MERGE (l11)-[:HAS_ACCOUNT]->(a1)
MERGE (l11)-[:HAS_LIFECYCLE]->(lc1)
MERGE (l11)-[:IS_STATUS]->(s2)

MERGE (l12)-[:HAS_CONTACT]->(c2)
MERGE (l12)-[:HAS_ACCOUNT]->(a2)
MERGE (l12)-[:HAS_LIFECYCLE]->(lc2)
MERGE (l12)-[:IS_STATUS]->(s3)

MERGE (l13)-[:HAS_CONTACT]->(c3)
MERGE (l13)-[:HAS_ACCOUNT]->(a3)
MERGE (l13)-[:HAS_LIFECYCLE]->(lc1)
MERGE (l13)-[:IS_STATUS]->(s4)

MERGE (l14)-[:HAS_CONTACT]->(c4)
MERGE (l14)-[:HAS_ACCOUNT]->(a4)
MERGE (l14)-[:HAS_LIFECYCLE]->(lc2)
MERGE (l14)-[:IS_STATUS]->(s5)

MERGE (l15)-[:HAS_CONTACT]->(c5)
MERGE (l15)-[:HAS_ACCOUNT]->(a5)
MERGE (l15)-[:HAS_LIFECYCLE]->(lc1)
MERGE (l15)-[:IS_STATUS]->(s6)

MERGE (l16)-[:HAS_CONTACT]->(c6)
MERGE (l16)-[:HAS_ACCOUNT]->(a1)
MERGE (l16)-[:HAS_LIFECYCLE]->(lc2)
MERGE (l16)-[:IS_STATUS]->(s1)

MERGE (l17)-[:HAS_CONTACT]->(c7)
MERGE (l17)-[:HAS_ACCOUNT]->(a2)
MERGE (l17)-[:HAS_LIFECYCLE]->(lc1)
MERGE (l17)-[:IS_STATUS]->(s2)

MERGE (l18)-[:HAS_CONTACT]->(c8)
MERGE (l18)-[:HAS_ACCOUNT]->(a3)
MERGE (l18)-[:HAS_LIFECYCLE]->(lc2)
MERGE (l18)-[:IS_STATUS]->(s3)

MERGE (l19)-[:HAS_CONTACT]->(c9)
MERGE (l19)-[:HAS_ACCOUNT]->(a4)
MERGE (l19)-[:HAS_LIFECYCLE]->(lc1)
MERGE (l19)-[:IS_STATUS]->(s4)

MERGE (l20)-[:HAS_CONTACT]->(c10)
MERGE (l20)-[:HAS_ACCOUNT]->(a5)
MERGE (l20)-[:HAS_LIFECYCLE]->(lc2)
MERGE (l20)-[:IS_STATUS]->(s5)
