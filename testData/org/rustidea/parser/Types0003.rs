struct A<T> {
    array: [i32; 4],
    slice: [i32], // from the grammar point of view, pure slice types are ok
    array2: [Vec<T>; 25]
}