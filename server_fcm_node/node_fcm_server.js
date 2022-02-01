const admin = require('firebase-admin');
const serviceAccount = require('./server_key.json');

admin.initializeApp({
    credential: admin.credential.cert(serviceAccount)
})
const db = admin.firestore();
const usersRef = db.collection('users');
usersRef.where('email', '==', 'rrilla01@gmail.com').get().then(doc => {
    if (doc.empty) {
        console.log('No matching documents.');
        return;
    }
    doc.forEach(doc => {
        console.log(doc.id, '=>', doc.data());
        console.log(doc.data().token);
        const token = doc.data().token;
        const fcm_msg = {
            notification: {
                title: 'noti title',
                body: 'noti body'
            },
            data: {
                title: "데이터제목",
                value: "바디"
            },
            token: token
        }
        admin.messaging().send(fcm_msg)
            .then(function(response){
                console.log('send ok...')
            })
            .catch(function(error){
                console.log('send error...')
            })
    });
});