# GlassLibrary - Components with glass effect
With this library you can create windows (jframe) and components with Glassmorphism effect with high reliability and quality.
> Important: This code for now should only work on Windows operating system, more specifically on version 10 build 17134 or later.
### Motivation
While there are already other libraries and code that do the same thing, most of them are either broken or will break your code/application (both visually and functionally), so I decided to create a library that was more "uniform" and compatible with component customization
### Get started
First of all, make sure your application is running on Java 16 or later.

It's very simple to use GlassLibrary, just pay attention to some methods when creating your application and everything will be fine.

To start using GlassLibrary access the Wiki by [clicking here](https://github.com/SrBalbucio/GlassLibrary/wiki).<br><br>
Quick code example:
```java
import balbucio.glasslibrary.GlassLibrary;

GlassLibrary lib = new GlassLibrary();
if(lib.isSupported()){
    GlassFrame frame = new GlassFrame(String: title);
    // Use these methods instead of the traditional ones
    // frame.addComponent(Component: c, Object o);
    // frame.layout(LayoutManager: manager);
    frame.setSize(640, 480);
}
```
When running you should have something like:
![](screenshot/img.png)



