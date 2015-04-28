

$('.change-btn').on('click', function(e){
	e.preventDefault();
	var taskId = $(this).data('task-id');
	$('.title.' + taskId).focus();
})

$('.new-task-btn').on('click', function(e){
	e.preventDefault();

	// Switch views
	$('.task-list').hide();
	$('.create').show();

	// Switch buttons
	$('.new-task-btn').hide();
	$('.save-task-btn').show();
})

$('.save-task-btn').on('click', function(e){
	e.preventDefault();
	$('.create').submit();
})

$('.delete-task-btn').on('click', function(e){
	e.preventDefault();
	var form = $(this).parent();
	form.submit();
})

$('.remind-task-btn').on('click', function(e){
	e.preventDefault();
	var form = $(this).parent();
	form.submit();
})
