CustomDialog 
===================
[![](https://jitpack.io/v/EunsilJo/CustomDialog.svg)](https://jitpack.io/#EunsilJo/CustomDialog) [![API](https://img.shields.io/badge/API-15%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=15)

Android Library that shows simple and customizable **flat designed dialogs** with no title.

## How to import
Add it in your root build.gradle at the end of repositories:
```java
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Add the dependency
```java
dependencies {
	compile 'com.github.EunsilJo:CustomDialog:1.0.3'
}
```

## How to use
### CustomDialog
<img src="https://github.com/EunsilJo/CustomDialog/blob/master/screenshots/2.png?raw=true" height="400"/> <img src="https://github.com/EunsilJo/CustomDialog/blob/master/screenshots/1.png?raw=true" height="400" /> <img src="https://github.com/EunsilJo/CustomDialog/blob/master/screenshots/10.png?raw=true" height="400"/>

```java
new CustomDialog.Builder(activity)
        .content("The verification e-mail sent. Please check.")
        .neutralText("OK")
        .onNeutral(new CustomDialog.DialogButtonCallback(){...})
        .action(CustomDialog.DialogAction.NEUTRAL)
        .cancelable(false)
        .build()
        .show();
```
```java
new CustomDialog.Builder(activity)
        .content("Are you sure you want to sign out?")
        .positiveText("Sign Out")
        .onPositive(new CustomDialog.DialogButtonCallback(){...})
        .negativeText("Cancel")
        .onNegative(new CustomDialog.DialogButtonCallback(){...})
        .cancelable(false)
        .build()
        .show();
```
You can change color of buttons.
```java
        .positiveDrawable(R.color.colorAccent)
        .negativeColor(Color.parseColor("#212121"))
```

### CustomInputDialog
<img src="https://github.com/EunsilJo/CustomDialog/blob/master/screenshots/3.png?raw=true" height="400"/> <img src="https://github.com/EunsilJo/CustomDialog/blob/master/screenshots/4.png?raw=true" height="400"/>

```java
new CustomInputDialog.Builder(activity)
        .hint("Please enter your review.")
        .maxLines(2)
        .maxLength(50)
        .showLength(true)
        .inputCallback(new CustomInputDialog.DialogInputCallback(){...})
        .inputColor(CustomDialog.DialogColor.PRIMARYDARK)
        .neutralText("OK")
        .action(CustomDialog.DialogAction.NEUTRAL)
        .cancelable(true)
        .build()
        .show();
```
You can set text of prefill.
```java
        .prefill("www.instagram.com/")
        .showLength(false)
```

### CustomSelectDialog
<img src="https://github.com/EunsilJo/CustomDialog/blob/master/screenshots/5.png?raw=true" height="400"/> <img src="https://github.com/EunsilJo/CustomDialog/blob/master/screenshots/7.png?raw=true" height="400"/> <img src="https://github.com/EunsilJo/CustomDialog/blob/master/screenshots/6.png?raw=true" height="400"/>

```java
new CustomSelectDialog.Builder(activity)
        .items(Arrays.asList("Apple", "Banana", "Cherry", "Durian", "Melon", "Orange"))
        .selectedItems(new ArrayList<>(Arrays.asList(1, 2, 5))
        .choice(SelectButtonAdapter.CHOICE_MODE.MULTIPLE)
        .type(CustomSelectDialog.DialogSelectType.LIST)
        .multiChoiceListCallback(new CustomSelectDialog.DialogMultiChoiceListCallback(){...})
        .selectColor(CustomDialog.DialogColor.PRIMARY)
        .action(CustomDialog.DialogAction.NEUTRAL)
        .cancelable(true)
        .build()
        .show();
```
You can show select list with button style.
```java
        .type(CustomSelectDialog.DialogSelectType.BUTTON)
```
You can set single choice list.
```java
        .selectedItem(1)
        .choice(SelectButtonAdapter.CHOICE_MODE.SINGLE)
```

### CustomDatePickerDialog
<img src="https://github.com/EunsilJo/CustomDialog/blob/master/screenshots/8.png?raw=true" height="400"/> <img src="https://github.com/EunsilJo/CustomDialog/blob/master/screenshots/9.png?raw=true" height="400"/>

```java
new CustomDatePickerDialog.Builder(activity)
        .year(2017)
        .monthOfYear(9)
        .dayOfMonth(27)
        .showYear(true)
        .showMonth(true)
        .showDay(true)
        .datePickerCallback(new CustomDatePickerDialog.DialogDatePickerCallback(){...})
        .dividerColor(CustomDialog.DialogColor.PRIMARY)
        .cancelable(true)
        .build()
        .show();
```
You can show/hide year, month and day.
```java
        .showDay(false)
```

#### v1.0.2 updated

### CustomTimePickerDialog
<img src="https://github.com/EunsilJo/CustomDialog/blob/master/screenshots/11.png?raw=true" height="400"/> <img src="https://github.com/EunsilJo/CustomDialog/blob/master/screenshots/12.png?raw=true" height="400"/>

```java
new CustomTimePickerDialog.Builder(activity)
        .hour(18)
        .minute(23)
        .timePickerCallback(new CustomTimePickerDialog.DialogTimePickerCallback(){...})
        .cancelable(true)
        .build()
        .show();
```
You can show/hide AM/PM.
```java
        .is24HourView(true)
```

### CustomStartEndPickerDialog
<img src="https://github.com/EunsilJo/CustomDialog/blob/master/screenshots/13.png?raw=true" height="400"/>

```java
new CustomStartEndPickerDialog.Builder(activity)
        .startHour(11)
        .startMinute(20)
        .endHour(13)
        .endMinute(20)
        .startEndPickerCallback(CustomStartEndPickerDialog.DialogStartEndPickerCallback(){...})
        .cancelable(true)
        .build()
        .show();
```
You can pick start and end time.

### CustomMultiPickerDialog
<img src="https://github.com/EunsilJo/CustomDialog/blob/master/screenshots/14.png?raw=true" height="400"/>

```java
new CustomMultiPickerDialog.Builder(activity)
        .number1(2)
        .minMaxValue1(1, 1000)
        .number2(2)
        .minMaxValue2(0, maxValue2)
        .displayValues2(new String[]{"Apple", "Banana", "Cherry", "Durian", "Melon", "Orange"})
        .multiPickerCallback(CustomMultiPickerDialog.DialogMultiPickerCallback(){...})
        .cancelable(true)
        .build()
        .show();
```
You can pick two items.

#### v1.0.3 updated

### CustomDatePickerDialog

```java
    public interface DialogDatePickerCallback {
        void onDateChanged(@NonNull CustomDatePickerDialog dialog, DatePicker view, int year, int monthOfYear, int dayOfMonth);
        ...
    }
```
* *onDateChanged* : The callback used to indicate the user changed the date.

### +
Please check the demo app to see examples.