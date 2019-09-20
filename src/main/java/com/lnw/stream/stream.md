Stream是高级的迭代器

# Stream提供的功能
外部迭代，内部迭代
惰性求值，并行，短路操作
中间操作，终止操作


# 中间操作
中间操作：无状态操作、有状态操作
区别：无状态表示当前的结果不依赖于其他元素，有状态表示当前的结果依赖于前面的元素

| | 相关方法 |
| 无状态 | map/mapToXxx |
|       | flatMap/flatMapToXxx |
|       | filter |
|       | peek |
|       | unordered |
| 有状态 | distinct |
|       | sorted |
|       | limit/skip |

