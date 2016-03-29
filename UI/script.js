var authorName;
var messageTxt;
var messagesList = [];

function run() {
	var appContainer = document.getElementsByClassName('container')[0];
	
	appContainer.addEventListener('click', delegateEvent);
	authorName = loadAuthor();
	var authorInput = document.getElementById('author-input');
	authorInput.value = authorName;
	messagesList = loadMessages() || [
		newMessage('admin', 'Добро пожаловать в Уютный чатик', Date.now(), false)
	];
	render(messagesList);
}

function delegateEvent(event) {
	if (event.type === 'click') {
		if (event.target.id == 'add-button') onAddButtonClick(event);
		if (event.target.classList.contains('delete-button')) deleteMessage(event);
		if (event.target.classList.contains('message-txt')) editMessage(event);
	}
}

function saveAuthorName(nameToSave) {
	if (typeof(Storage) == 'undefined') {
		alert('localStorage is not accessible');
		return;
	}

	localStorage.setItem('authorName', JSON.stringify(nameToSave))
}

function loadAuthor() {
	if (typeof(Storage) == 'undefined') {
		alert('localStorage is not accessible');
		return;
	}

	var item = localStorage.getItem('authorName');
	return item && JSON.parse(item);
}

function saveMessages(listToSave) {
	if (typeof(Storage) == 'undefined') {
		alert('localStorage is not accessible');
		return;
	}

	localStorage.setItem('messagesList', JSON.stringify(listToSave))
}

function loadMessages() {
	if (typeof(Storage) == 'undefined') {
		alert('localStorage is not accessible');
		return;
	}

	var item = localStorage.getItem('messagesList');
	return item && JSON.parse(item);
}

function render(messages) {
	for (var i = 0; i < messages.length; i++) {
		renderMessage(messages[i]);
	}
}

function renderMessage(message) {
	var messages = document.getElementById('messages');
	var element = elementFromTemplate();

	renderMessageState(element, message);
	messages.appendChild(element);
}

function renderMessageState(element, message) {
	if (message.deleted) element.getElementsByClassName('message-txt')[0].innerHTML = 'Сообщение удалено';
	else element.getElementsByClassName('message-txt')[0].innerHTML = '<button class="delete-button" type="button"><div class="del-character">×</div></button>' + message.content;
	element.getElementsByClassName('message-author')[0].innerHTML = message.author;
	var date = new Date(message.date);
	element.getElementsByClassName('message-date')[0].innerHTML = " " + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds() + ", " + date.getDate() + "." + date.getMonth() + "." + date.getFullYear();
	element.setAttribute('data-message-id', message.id);	
}

function elementFromTemplate() {
	var template = document.getElementById('message-template');
	return template.firstElementChild.cloneNode(true);
}

function deleteMessage(event) {
	messagesList[event.target.parentNode.parentNode.getAttribute('data-message-id')].deleted = 1;
	saveMessages(messagesList);
	event.target.parentNode.className = '';
	event.target.parentNode.style.margin = '1px 0 0 0';
	event.target.parentNode.innerHTML = 'Сообщение удалено';
}

function editMessage(event) {
	var oldMessage = event.target.parentNode;
	authorName = event.target.parentNode.getElementsByClassName('message-author')[0].innerHTML;
	messageTxt = prompt('Новое сообщение:', '');
	if (messageTxt == null || messageTxt == '') {
		return;
	}
	event.target.innerHTML = '<button class="delete-button" type="button"><div class="del-character">×</div></button>' + messageTxt;
	messagesList[event.target.parentNode.getAttribute('data-message-id')].content = messageTxt;
	messagesList[event.target.parentNode.getAttribute('data-message-id')].date = Date.now();
	saveMessages(messagesList);
}

function onAddButtonClick(event) {
	authorName = document.getElementById('author-input');
	messageInput = document.getElementById('txt-input');

	var message = newMessage(authorName.value, messageInput.value, new Date(), false); 

	if (messageInput.value == '' || authorName.value == '') return;

	messagesList.push(message);
	messageInput.value = '';
	render([message]);
	saveMessages(messagesList);
	saveAuthorName(authorName.value);
}

function newMessage(name, text, dates, isDeleted) {
	return {
		author: name,
		content: text,
		date: dates,
		deleted: !!isDeleted,
		id: '' + uniqueId()
	};
}

function uniqueId() {
	return messagesList.length;
}