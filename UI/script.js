var authorName;
var messageTxt;

function run(){
    var appContainer = document.getElementsByClassName('container')[0];
    appContainer.addEventListener('click', delegateEvent);
}

function delegateEvent(evtObj) {
    if (evtObj.type === 'click') {
        if (evtObj.target.id == 'add-button') {
            onAddButtonClick(evtObj);
        }
        if (evtObj.target.classList.contains('delete-button')) {
            deleteMessage(evtObj);
        }
        if (evtObj.target.classList.contains('message-txt')) {
            editMessage(evtObj);
        }
    }
}

function deleteMessage(evtObj) {
    evtObj.target.parentNode.className = '';
    evtObj.target.parentNode.style.margin = '1px 0 0 0';
    evtObj.target.parentNode.innerHTML = 'Сообщение удалено';
}

function editMessage(evtObj) {
    var oldMessage = evtObj.target.parentNode;
    authorName = evtObj.target.parentNode.getElementsByClassName('message-author')[0].innerHTML;
    messageTxt = prompt('Новое сообщение:', '');
    if (messageTxt == null || messageTxt == '') {
        alert("try again!");
        return;
    }
    var newMessage = createMessage(messageTxt);
    oldMessage.parentNode.replaceChild(newMessage, oldMessage);
}

function onAddButtonClick() {
    authorName = document.getElementById('author-input').value;
    messageTxt = document.getElementById('txt-input');

    var newMessage = createMessage(messageTxt.value); 
    messageTxt.value = '';
    var messages = document.getElementById('messages');
    messages.appendChild(newMessage);
}

function createMessage(messageTxt) {
    if(!authorName || !messageTxt) { return; }
    
    var messageDiv = document.createElement('div');
    var messageHeaderDiv = document.createElement('div');
    var messageAuthorB = document.createElement('b');
    var messageDateSup = document.createElement('sup');
    var messageDelButton = document.createElement('button');
    var messageDelButtonChar = document.createElement('div');
    var messageTxtDiv = document.createElement('div');

    messageDiv.classList.add('message');
    messageHeaderDiv.classList.add('message-header');
    messageAuthorB.classList.add('message-author');
    messageDateSup.classList.add('message-date');
    messageDelButton.classList.add('delete-button');
    messageDelButtonChar.classList.add('del-character');
    messageTxtDiv.classList.add('message-txt');

    messageDiv.appendChild(messageHeaderDiv);
    messageHeaderDiv.appendChild(messageAuthorB);
    messageHeaderDiv.appendChild(messageDateSup);
    messageDiv.appendChild(messageTxtDiv);
    messageAuthorB.appendChild(document.createTextNode(authorName));
    messageDelButton.appendChild(messageDelButtonChar);
    messageDelButtonChar.appendChild(document.createTextNode('×'));
    messageTxtDiv.appendChild(messageDelButton);
    messageTxtDiv.appendChild(document.createTextNode(messageTxt));
    var date = new Date();
    messageDateSup.appendChild(document.createTextNode(" " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ", " + date.getDate() + "." + date.getMonth() + "." + date.getFullYear()));

    return messageDiv;
}