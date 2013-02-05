---
layout: default
title: Scalaz's Validation
tested_by: "scalaz/ValidationSpec"
---
* the [`Validation`](https://github.com/scalaz/scalaz/blob/master/core/src/main/scala/scalaz/Validation.scala) trait is **not** about performing validation, but of recording success/failure;
* probably best to consider [type definitions](https://github.com/mattdenner/scala-playground/blob/master/src/test/scala/com/denner/scalaz/ValidationSpec.scala#L17) when dealing with this trait, to make the code more readable;
* it keeps the semantics of `Either[L,R]` where the `Left` is for failure, `Failure[L]`, and the `Right` is for success, `Success[R]`, for `Validation[L,R]`, and includes [`validation`](https://github.com/scalaz/scalaz/blob/master/core/src/main/scala/scalaz/Validation.scala#L127) specifically for this;
* once you've got an instance then typical functions, like `map` and `foreach`, apply only to success, not executing for failures;
* and [`Validation[L,R].toOption[R]`](https://github.com/scalaz/scalaz/blob/master/core/src/main/scala/scalaz/Validation.scala#L38) will return a `Some[R]` for success and `None[R]` for failure.

I'm slightly bothered by the `Validation` trait, because it contains pattern matching for a specifically limited set of cases, as the trait is sealed.  I would say that, using
the inheritance hierarchy would be better, so putting the right implementations of the functions in `Success` and `Failure`, but this leads to duplication of the function
signature, which the gist below shows isn't good:

<script src="https://gist.github.com/4703562.js">
</script>

So I'm learning that sometimes my old object-oriented brain isn't always going to be right in the functional world!
