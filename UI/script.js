function run(){
    var appContainer = document.getElementById('submit-button');
    appContainer.addEventListener('click', delegateEvent);
}

function delegateEvent(evtObj) {
    if(evtObj.type === 'click') {
        onAddButtonClick(evtObj);
    }
}

function onAddButtonClick(){
    var authorName = document.getElementById('author-input');
    var messageTxt = document.getElementById('txt-input');

    addMessage(authorName.value, messageTxt.value);
    authorName.value = '';
    messageTxt.value = '';
}

function addMessage(authorName, messageTxt) {
    if(!authorName || !messageTxt) { return; }
    
    var messageDiv = document.createElement('div');
    var messageHeaderDiv = document.createElement('div');
    var messageAuthorB = document.createElement('b');
    var messageDateSup = document.createElement('sup');
    var messageTxtDiv = document.createElement('div');

    messageDiv.classList.add('message');
    messageHeaderDiv.classList.add('message-header');
    messageAuthorB.classList.add('message-author');
    messageDateSup.classList.add('message-date');
    messageTxtDiv.classList.add('message-txt');

    messageDiv.appendChild(messageHeaderDiv);
    messageHeaderDiv.appendChild(messageAuthorB);
    messageHeaderDiv.appendChild(messageDateSup);
    messageDiv.appendChild(messageTxtDiv);
    messageAuthorB.appendChild(document.createTextNode(authorName));
    messageTxtDiv.appendChild(document.createTextNode(messageTxt));
    var date = new Date();
    messageDateSup.appendChild(document.createTextNode(date.toUTCString()));

    var messages = document.getElementById('messages-body');

    messages.appendChild(messageDiv);
}